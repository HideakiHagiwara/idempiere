/******************************************************************************
 * Copyright (C) 2008 Low Heng Sin                                            *
 * Copyright (C) 2008 Idalica Corporation                                     *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 *****************************************************************************/
package org.adempiere.webui.desktop;

import java.util.List;

import org.adempiere.util.Callback;
import org.adempiere.webui.AdempiereIdGenerator;
import org.adempiere.webui.adwindow.ADWindow;
import org.adempiere.webui.apps.ProcessDialog;
import org.adempiere.webui.apps.wf.WFPanel;
import org.adempiere.webui.component.DesktopTabpanel;
import org.adempiere.webui.component.Tab.DecorateInfo;
import org.adempiere.webui.component.Tabbox;
import org.adempiere.webui.component.Tabpanel;
import org.adempiere.webui.component.Window;
import org.adempiere.webui.factory.InfoManager;
import org.adempiere.webui.panel.ADForm;
import org.adempiere.webui.panel.IHelpContext;
import org.adempiere.webui.panel.InfoPanel;
import org.adempiere.webui.part.WindowContainer;
import org.adempiere.webui.util.Icon;
import org.adempiere.webui.util.ZKUpdateUtil;
import org.adempiere.webui.window.Dialog;
import org.adempiere.webui.window.WTask;
import org.compiere.model.MForm;
import org.compiere.model.MInfoWindow;
import org.compiere.model.MProcess;
import org.compiere.model.MQuery;
import org.compiere.model.MTask;
import org.compiere.model.SystemProperties;
import org.compiere.util.Env;
import org.compiere.wf.MWorkflow;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Iframe;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabpanels;

/**
 * Abstract base class for Tabbed MDI implementation
 * @author hengsin
 */
public abstract class TabbedDesktop extends AbstractDesktop {
	/** Controller for open desktop windows. */
	protected WindowContainer windowContainer;

	/**
	 * Default constructor
	 */
	public TabbedDesktop() {
		super();
		windowContainer = new WindowContainer();
	}

	/**
     * @param processId
     * @param soTrx
     * @return ProcessDialog
     */
	@Override
	public ProcessDialog openProcessDialog(int processId, boolean soTrx) {
		ProcessDialog pd = new ProcessDialog (processId, soTrx, getPredefinedContextVariables());

		if (pd.isValid()) {
			DesktopTabpanel tabPanel = new DesktopTabpanel();
			pd.setParent(tabPanel);
			String title = pd.getTitle();
			pd.setTitle(null);
			preOpenNewTab();
			DecorateInfo decorateInfo = new DecorateInfo(MProcess.get(processId).isReport()?Icon.REPORT:Icon.PROCESS);
			windowContainer.addWindow(tabPanel, title, true, decorateInfo);
			Events.postEvent(ProcessDialog.ON_INITIAL_FOCUS_EVENT, pd, null);
		}
		return pd;
	}

    /**
     * @param formId
     * @return ADForm
     */
	@Override
	public ADForm openForm(int formId) {
		ADForm form = ADForm.openForm(formId, null, null, getPredefinedContextVariables(), isMenuSOTrx());

		if (Window.Mode.EMBEDDED == form.getWindowMode()) {
			DesktopTabpanel tabPanel = new DesktopTabpanel();
			form.setParent(tabPanel);
			//do not show window title when open as tab
			form.setTitle(null);
			preOpenNewTab();
			windowContainer.addWindow(tabPanel, form.getFormName(), true, DecorateInfo.get(MForm.get(formId)));
			form.focus();
		} else {
			form.setAttribute(Window.MODE_KEY, form.getWindowMode());
			showWindow(form);
		}

		return form;
	}

	/**
	 * @param infoId
	 */
	@Override
	public void openInfo(int infoId) {
		InfoPanel infoPanel = InfoManager.create(infoId, getPredefinedContextVariables());
		
		if (infoPanel != null) {
			DesktopTabpanel tabPanel = new DesktopTabpanel();
			infoPanel.setParent(tabPanel);
			String title = infoPanel.getTitle();
			infoPanel.setTitle(null);
			preOpenNewTab();
			windowContainer.addWindow(tabPanel, title, true, DecorateInfo.get(MInfoWindow.get(infoId, null)));
			infoPanel.focus();
		} else {
			Dialog.error(0, "NotValid");
		}
	}
	
	/**
	 * @param workflow_ID
	 */
	@Override
	public void openWorkflow(int workflow_ID) {
		WFPanel p = new WFPanel();
		p.load(workflow_ID);

		DesktopTabpanel tabPanel = new DesktopTabpanel();
		p.setParent(tabPanel);
		preOpenNewTab();
		windowContainer.addWindow(tabPanel, p.getWorkflow().get_Translation(MWorkflow.COLUMNNAME_Name), true, new DecorateInfo(Icon.WORKFLOW));
	}
	
	/**
	 * @param windowId
	 * @param callback
	 */
	@Override
	public void openWindow(int windowId, Callback<ADWindow> callback) {
		openWindow(windowId, null, callback);
	}

	/**
	 * @param windowId
     * @param query
     * @param callback
	 */
	@Override
	public void openWindow(int windowId, MQuery query, Callback<ADWindow> callback) {
		final ADWindow adWindow = new ADWindow(Env.getCtx(), windowId, query);

		final DesktopTabpanel tabPanel = new DesktopTabpanel();
		int windowNo = adWindow.getADWindowContent().getWindowNo();
		if (SystemProperties.isZkUnitTest()) {
			String id = AdempiereIdGenerator.escapeId(adWindow.getTitle());
			tabPanel.setId(id+"_"+windowNo);
		}
		final Tab tab = windowContainer.addWindow(tabPanel, adWindow.getTitle(), true, DecorateInfo.get(adWindow));

		tab.setClosable(false);		
		final OpenWindowRunnable runnable = new OpenWindowRunnable(adWindow, tab, tabPanel, callback);
		Env.setPredefinedVariables(Env.getCtx(), windowNo, getPredefinedContextVariables());
		preOpenNewTab();
		runnable.run();
	}

	/**
     * @param taskId
     */
	@Override
	public void openTask(int taskId) {
		MTask task = new MTask(Env.getCtx(), taskId, null);
		new WTask(task.getName(), task);
	}

	/**
	 * @param url
	 */
	@Override
	public void showURL(String url, boolean closeable)
    {
    	showURL(url, url, closeable);
    }

	/**
	 * @param url
	 * @param title
	 * @param closeable
	 */
    public void showURL(String url, String title, boolean closeable)
    {
    	Iframe iframe = new Iframe(url);
    	addWin(iframe, title, closeable);
    }

    /**
     * @param content HTML content
     * @param title
     * @param closeable
     */
    @Override
    public void showHTMLContent(String content, String title, boolean closeable)
    {
    	Iframe iframe = new Iframe();

    	AMedia media = new AMedia(title, "html", "text/html", content.getBytes());
    	iframe.setContent(media);

    	addWin(iframe, title, closeable);
    }

    /**
     * @param fr Iframe
     * @param title
     * @param closeable
     */
    private void addWin(Iframe fr, String title, boolean closeable)
    {
    	ZKUpdateUtil.setWidth(fr, "100%");
        ZKUpdateUtil.setHeight(fr, "100%");
        fr.setStyle("padding: 0; margin: 0; border: none; position: absolute");
        Window window = new Window();
        ZKUpdateUtil.setWidth(window, "100%");
        ZKUpdateUtil.setHeight(window, "100%");
        window.setStyle("padding: 0; margin: 0; border: none");
        window.appendChild(fr);
        window.setStyle("position: absolute");

        Tabpanel tabPanel = new Tabpanel();
    	window.setParent(tabPanel);
    	preOpenNewTab();
    	windowContainer.addWindow(tabPanel, title, closeable, null);
    }

    /**
     * @param AD_Window_ID
     * @param query
     */
    @Override
    public void showZoomWindow(int AD_Window_ID, MQuery query)
    {
    	final ADWindow wnd = new ADWindow(Env.getCtx(), AD_Window_ID, query);

    	final DesktopTabpanel tabPanel = new DesktopTabpanel();		
		final Tab tab = windowContainer.insertAfter(windowContainer.getSelectedTab(), tabPanel, wnd.getTitle(), true, true, DecorateInfo.get(wnd));
		tab.setClosable(false);		
		final OpenWindowRunnable runnable = new OpenWindowRunnable(wnd, tab, tabPanel, null);
		preOpenNewTab();
		runnable.run();
	}

    /**
     * @param AD_Window_ID
     * @param query
     * @deprecated
     */
    @Deprecated
    public void showWindow(int AD_Window_ID, MQuery query)
    {
    	openWindow(AD_Window_ID, query, null);
	}

	/**
	 * Show window in new tab
	 * @param window
	 */
	protected void showEmbedded(Window window)
   	{
		Tabpanel tabPanel = new Tabpanel();
    	window.setParent(tabPanel);
    	Object defer = window.getAttribute(WindowContainer.DEFER_SET_SELECTED_TAB);
    	if ( defer != null)
    		tabPanel.setAttribute(WindowContainer.DEFER_SET_SELECTED_TAB, defer);
    	String title = window.getTitle();
    	window.setTitle(null);
    	preOpenNewTab();
    	DecorateInfo decorateInfo = (DecorateInfo) window.getAttribute(Window.DECORATE_INFO);
    	if(decorateInfo==null)
    		decorateInfo = new DecorateInfo(Icon.TAB);
    	if (Window.INSERT_NEXT.equals(window.getAttribute(Window.INSERT_POSITION_KEY))) {
    		windowContainer.insertAfter(windowContainer.getSelectedTab(), tabPanel, title, true, true, decorateInfo);
		}
		else if(Window.REPLACE.equals(window.getAttribute(Window.INSERT_POSITION_KEY))) {
			Tab refTab = windowContainer.getSelectedTab();
			Object windowNoAttribute = window.getAttribute(WindowContainer.REPLACE_WINDOW_NO);
			if (windowNoAttribute != null && windowNoAttribute instanceof Integer) {
				int windowNo = (Integer)windowNoAttribute;
				refTab = windowContainer.getTab(windowNo);
			}

			if (refTab == null)
				windowContainer.addWindow(tabPanel, title, true, decorateInfo);
			else
				windowContainer.replace(refTab, window, title);

		}
		else {
	    	windowContainer.addWindow(tabPanel, title, true, decorateInfo);
		}
    	if (window instanceof IHelpContext)
			Events.sendEvent(new Event(WindowContainer.ON_WINDOW_CONTAINER_SELECTION_CHANGED_EVENT, window));
   	}

	/**
	 * Close active tab
	 * @return boolean
	 */
	public boolean closeActiveWindow()
	{
		if (windowContainer.getSelectedTab() != null)
		{
			Tabpanel panel = (Tabpanel) windowContainer.getSelectedTab().getLinkedPanel();
			Component component = panel.getFirstChild();
			Object att = component.getAttribute(WINDOWNO_ATTRIBUTE);

			if ( windowContainer.closeActiveWindow() )
			{
				if (att != null && (att instanceof Integer))
				{
					unregisterWindow((Integer) att);
				}
				return true;
			}
			else
			{
				return false;
			}
		}
		return false;
	}

	/**
	 * Get component of active tab
	 * @return Component
	 */
	@Override
	public Component getActiveWindow()
	{
		return windowContainer.getSelectedTab().getLinkedPanel().getFirstChild();
	}

	/**
	 * @param windowNo
	 * @return true if found and close
	 */
	@Override
	public boolean closeWindow(int windowNo)
	{
		Tabbox tabbox = windowContainer.getComponent();
		Tabpanels panels = tabbox.getTabpanels();
		List<?> childrens = panels.getChildren();
		for (Object child : childrens)
		{
			Tabpanel panel = (Tabpanel) child;
			Component component = panel.getFirstChild();
			Object att = component != null ? component.getAttribute(WINDOWNO_ATTRIBUTE) : null;
			if (att != null && (att instanceof Integer))
			{
				if (windowNo == (Integer)att)
				{
					Tab tab = panel.getLinkedTab();
					if (tab != null) {
						panel.getLinkedTab().onClose();
						if (tab.getParent() == null)
						{
							unregisterWindow(windowNo);
							return true;
						}
						else
						{
							return false;
						}
					} else {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * invoke before a new tab is added to the desktop
	 */
	protected void preOpenNewTab()
	{
	}
	
	/**
	 * Runnable to create window content ({@link ADWindow#createPart(Object)})
	 */
	class OpenWindowRunnable implements Runnable {

		private final ADWindow adWindow;
		private final Tab tab;
		private final DesktopTabpanel tabPanel;
		private Callback<ADWindow> callback;

		/**
		 * @param adWindow
		 * @param tab
		 * @param tabPanel
		 * @param callback
		 */
		protected OpenWindowRunnable(ADWindow adWindow, Tab tab, DesktopTabpanel tabPanel, Callback<ADWindow> callback) {
			this.adWindow = adWindow;
			this.tab = tab;
			this.tabPanel = tabPanel;
			this.callback = callback;
		}
		
		@Override
		public void run() {			
			if (adWindow.createPart(tabPanel) != null ) {
				tab.setClosable(true);
				if (callback != null) {
					callback.onCallback(adWindow);
				}
			} else {
				tab.onClose();
			}
		}		
	}

	/**
	 * @param title
	 * @param windowNo 
	 */
	@Override
	public void setTabTitle(String title, int windowNo) {
		windowContainer.setTabTitle(title, windowNo);		
	}

}
