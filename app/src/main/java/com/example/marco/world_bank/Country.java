package com.example.marco.world_bank;

import com.google.gson.Gson;

public class Country {

    /**
     * id : ABW
     * iso2Code : AW
     * name : Aruba
     * region : {"id":"LCN","iso2code":"ZJ","value":"Latin America & Caribbean "}
     * adminregion : {"id":"","iso2code":"","value":""}
     * incomeLevel : {"id":"HIC","iso2code":"XD","value":"High income"}
     * lendingType : {"id":"LNX","iso2code":"XX","value":"Not classified"}
     * capitalCity : Oranjestad
     * longitude : -70.0167
     * latitude : 12.5167
     */

    private String id;
    private String iso2Code;
    private String name;
    private RegionBean region;
    private AdminregionBean adminregion;
    private IncomeLevelBean incomeLevel;
    private LendingTypeBean lendingType;
    private String capitalCity;
    private String longitude;
    private String latitude;

    public static Country objectFromData(String str) {

        return new Gson().fromJson(str, Country.class);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIso2Code() {
        return iso2Code;
    }

    public void setIso2Code(String iso2Code) {
        this.iso2Code = iso2Code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RegionBean getRegion() {
        return region;
    }

    public void setRegion(RegionBean region) {
        this.region = region;
    }

    public AdminregionBean getAdminregion() {
        return adminregion;
    }

    public void setAdminregion(AdminregionBean adminregion) {
        this.adminregion = adminregion;
    }

    public IncomeLevelBean getIncomeLevel() {
        return incomeLevel;
    }

    public void setIncomeLevel(IncomeLevelBean incomeLevel) {
        this.incomeLevel = incomeLevel;
    }

    public LendingTypeBean getLendingType() {
        return lendingType;
    }

    public void setLendingType(LendingTypeBean lendingType) {
        this.lendingType = lendingType;
    }

    public String getCapitalCity() {
        return capitalCity;
    }

    public void setCapitalCity(String capitalCity) {
        this.capitalCity = capitalCity;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public static class RegionBean {
        /**
         * id : LCN
         * iso2code : ZJ
         * value : Latin America & Caribbean
         */

        private String id;
        private String iso2code;
        private String value;

        public static RegionBean objectFromData(String str) {

            return new Gson().fromJson(str, RegionBean.class);
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIso2code() {
            return iso2code;
        }

        public void setIso2code(String iso2code) {
            this.iso2code = iso2code;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public static class AdminregionBean {
        /**
         * id :
         * iso2code :
         * value :
         */

        private String id;
        private String iso2code;
        private String value;

        public static AdminregionBean objectFromData(String str) {

            return new Gson().fromJson(str, AdminregionBean.class);
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIso2code() {
            return iso2code;
        }

        public void setIso2code(String iso2code) {
            this.iso2code = iso2code;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public static class IncomeLevelBean {
        /**
         * id : HIC
         * iso2code : XD
         * value : High income
         */

        private String id;
        private String iso2code;
        private String value;

        public static IncomeLevelBean objectFromData(String str) {

            return new Gson().fromJson(str, IncomeLevelBean.class);
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIso2code() {
            return iso2code;
        }

        public void setIso2code(String iso2code) {
            this.iso2code = iso2code;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public static class LendingTypeBean {
        /**
         * id : LNX
         * iso2code : XX
         * value : Not classified
         */

        private String id;
        private String iso2code;
        private String value;

        public static LendingTypeBean objectFromData(String str) {

            return new Gson().fromJson(str, LendingTypeBean.class);
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIso2code() {
            return iso2code;
        }

        public void setIso2code(String iso2code) {
            this.iso2code = iso2code;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
