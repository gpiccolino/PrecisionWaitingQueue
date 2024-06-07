package com.precisionpos.tv.waitingqueue.utils;

import com.precisionpos.tv.waitingqueue.beans.ListOrder;
import java.util.ArrayList;
import java.util.List;

/**
 * Generate page of orders
 */
public class PaginatorUtil {

    public List<ListOrder> orderList; // List of ALL orders
    public List<ListOrder> pageOrderList; // Sublist of orders displayed on the page
    public int TOTAL_ITEMS; // Total number of orders in the list
    public final int ITEMS_PER_PAGE = 7; // Max number of orders shown on a page
    public final int ITEMS_REMAINING = TOTAL_ITEMS % ITEMS_PER_PAGE; // Number of remaining orders
    public final int LAST_PAGE = TOTAL_ITEMS / ITEMS_PER_PAGE; // Max number of pages

    public PaginatorUtil(List<ListOrder> orderList) {
        TOTAL_ITEMS = orderList.size();
        this.orderList = orderList;
    }
    public PaginatorUtil() {}

    public void setOrderList(List<ListOrder> orderList) {
        this.orderList = orderList;
    }

    public List<ListOrder> generatePage(int currentPage) {

        try {
            int startIndex = currentPage * ITEMS_PER_PAGE;
            int numOfItems = ITEMS_PER_PAGE;

            pageOrderList = new ArrayList<>();

            if (currentPage == LAST_PAGE && ITEMS_REMAINING > 0) {
                for (int i = startIndex; i < (startIndex + ITEMS_REMAINING) && i < orderList.size(); i++) {
                    pageOrderList.add(orderList.get(i));
                }
            }
            else {
                for (int i = startIndex; i < (startIndex + numOfItems) && i < orderList.size(); i++) {
                    pageOrderList.add(orderList.get(i));
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return pageOrderList;
    }

    public int getTotalItems() {
        return TOTAL_ITEMS;
    }

    public int getItemsPerPage() {
        return ITEMS_PER_PAGE;
    }
}
