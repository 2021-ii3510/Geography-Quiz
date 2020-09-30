package fr.isep.ii3510.geographyquiz;

class Country {
    private String countryName, capitalCityName;

    public Country(String countryName, String capitalCityName) {
        this.countryName = countryName;
        this.capitalCityName = capitalCityName;
    }

    public String getCountryName() {
        return countryName;
    }

    public String getCapitalCityName() {
        return capitalCityName;
    }
}
