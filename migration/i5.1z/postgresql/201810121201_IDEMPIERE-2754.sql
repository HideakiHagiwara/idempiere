-- IDEMPIERE-2754 remove wrong test data.
-- Oct 12, 2018 12:00:11 PM CEST
DELETE FROM AD_Image WHERE AD_Image_ID=108
;

SELECT register_migration_script('201810121201_IDEMPIERE-2754.sql') FROM dual
;

