package com.getir.readingisgood.model.constants;

public final class URLConstants {

  //General
  public static final String CREATE = "/";
  public static final String UPDATE = "/";
  public static final String FIND_BY_ID = "/{id}";
  public static final String FIND_ALL = "/";

  //Customer
  public static final String CUSTOMER = "/customer";
  public static final String CUSTOMER_LOGIN = "/login";
  public static final String CUSTOMER_SIGN_UP = "/sign-up";
  public static final String CUSTOMER_ORDERS_PAGING = "/orders-paging";
  public static final String CUSTOMER_ORDERS_NON_PAGING = "/orders-non-paging";

  //Book
  public static final String BOOK = "/book";
  public static final String BOOK_UPDATE_STOCK = "/update-stock/{id}/{amount}";

  //ORDER
  public static final String ORDER = "/order";
  public static final String ORDER_BY_ORDER_DATE = "/{startDate}/{endDate}";

  //Statistic
  public static final String STATISTIC = "/statistic";
  public static final String STATISTIC_GET = "/";
}
