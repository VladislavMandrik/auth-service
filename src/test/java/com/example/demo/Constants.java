package com.example.demo;

public class Constants {
    // URLs
    public static final String BASE_URL = "http://localhost:8081/";

    // Language/Locale specific endpoints
    public static final String ABOUT_US_ENDPOINT = "/about-us/";
    public static final String ABOUT_US_RU_ENDPOINT = "/ru/about-us/";
    public static final String MAIN_PAGE_ENDPOINT = "/";
    public static final String LOGIN_PAGE_ENDPOINT = "/login";
    public static final String USER_ID4_PAGE_ENDPOINT = "/users/4";
    public static final String REGISTRATION_PAGE_ENDPOINT = "/registration";
    public static final String USERS_PAGE_ENDPOINT = "/users";

    // Search related
    public static final String SEARCH_KEYWORD = "test";
    public static final String SEARCH_KEYWORD_TYPO = "vbfbfg";

    // Timeouts
    public static final int EXPLICIT_WAIT_SECONDS = 15;

    // Error messages
    public static final String ERROR_SEARCH_CONTAINER_NOT_DISPLAYED = "Контейнер с результатами поиска не отображается";
    public static final String ERROR_SEARCH_CONTAINER_NOT_FOUND = "Поисковый контейнер не найден: ";
    public static final String ERROR_SEARCH_RESULTS_NOT_DISPLAYED = "Результаты поиска не отображаются";
    public static final String ERROR_LOGO_NOT_DISPLAYED = "Логотип не отображается";
    public static final String ERROR_VERIFYING = "Ошибка верификации поискового запроса: ";
    public static final String ERROR_HEADERS = "Заголовки результатов поиска не найдены";
    public static final String ERROR_CONTENT_HEADERS = "Ни один из %d заголовков не содержит '%s': %s";
    public static final String ERROR_EMPTY_CONTAINER = "Контейнер должен быть пустым";
    public static final String ERROR_ICON_WITH_TIMEOUT = "Иконка не кликабельна в течение " + EXPLICIT_WAIT_SECONDS + " секунд";

    // 404 Page expected texts
    public static final String TITLE_404 = "Страница не найдена - Innowise";
    public static final String CONTENT_404_EN = "The page you were looking for couldn't be found";

    // CSS Selectors
    public static final String SELECTOR_SEARCH_ICON = "div.bot-serach-cli";
    public static final String SELECTOR_INPUT_FIELD = "input[name='s']";
    public static final String SELECTOR_SEARCH_LOGO = "div.new-block-logo-subsite-link";
    public static final String SELECTOR_SEARCH_CONTAINER = "#content-masonry";
    public static final String SELECTOR_SEARCH_RESULTS = "#ajax-load-more .article-search";
    public static final String SELECTOR_RESULTS_TITLE = ".search-result";
    public static final String SELECTOR_FIND_RESULTS_HEADERS = ".article-search .entry-title a";

    //Search Pattern
    public static final String SEARCH_PATTERN = "property=\"og:title\" content=\"You searched for : %s - Innowise\"";
    public static final String JWT_TOKEN_PATTERN = "^[A-Za-z0-9-_]+\\.[A-Za-z0-9-_]+\\.[A-Za-z0-9-_]+$";
}
