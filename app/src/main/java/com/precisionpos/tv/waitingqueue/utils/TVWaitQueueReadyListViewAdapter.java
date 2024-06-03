package com.precisionpos.tv.waitingqueue.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.precisionpos.tv.waitingqueue.R;
import com.precisionpos.tv.waitingqueue.beans.ListOrder;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @since 12/29/22
 * @author Kate
 */
public class TVWaitQueueReadyListViewAdapter extends BaseAdapter {

    private final LayoutInflater inflater;
    private int offset;
    private int selectedColor;
    private int transparentColor        = Color.parseColor("#00000000");
    private int colorBgrdDark           = Color.parseColor("#424242");
    private int colorBgrdGreen		    = Color.parseColor("#caf7ad");
    private int colorTextGrey           = Color.parseColor("#a0a0a0");
    private int colorLabelWaiting	    = Color.parseColor("#e9312b");
    private int colorLabelReady		    = Color.parseColor("#2d9823");

    private List<ListOrder> orderList = new ArrayList<ListOrder>();
    private Context context;

    /**
     *
     * @param activity
     */
    public TVWaitQueueReadyListViewAdapter(Activity activity, Context context) {
        super();
        inflater 			= (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        selectedColor 		= Color.parseColor("#caf7ad");
        this.context        = context;
    }

    /**
     * Our view holder
     */
    private class ViewHolder {
        private TextView tvOrderRowNumber;
        private ImageView tvOrderIcon;
        private TextView tvOrderDateTime;
        private TextView tvOrderTicketNumber;
        private TextView tvOrderCustomerName;
        private TextView tvOrderWaitTime;
        private TextView tvOrderStatus;
    }

    /**
     * Set our order list
     * @param orderList
     */
    public void setOrderList (List<ListOrder> orderList) {
        this.orderList = orderList;

        // Refresh the list
        this.notifyDataSetChanged();
    }

    /*
     * (non-Javadoc)
     *
     * @see android.widget.Adapter#getCount()
     */
    public int getCount() {
        int count =  orderList == null ? 0 : orderList.size();
        return count;
    }

    /*
     * (non-Javadoc)
     *
     * @see android.widget.Adapter#getItem(int)
     */
    public Object getItem(int position) {
        return null;
    }

    public boolean isEnabled(int position) {
        if(orderList != null && position == orderList.size()) {
            return false;
        }

        return true;
    }

    /*
     * (non-Javadoc)
     *
     * @see android.widget.Adapter#getItemId(int)
     */
    public long getItemId(int position) {
        return 0;
    }

    public int getViewTypeCount() {
        return 1;
    }

    /**
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        // Get order header
        ListOrder orderHeaderBean = orderList.get(position);

        if (convertView == null || convertView.getTag() == null) {
            convertView = (LinearLayout) inflater.inflate(R.layout.activity_tv_order_row, null);

            viewHolder = new ViewHolder();
            viewHolder.tvOrderRowNumber			= ((TextView)convertView.findViewById(R.id.tv_order_row_number));
            viewHolder.tvOrderIcon		        = ((ImageView)convertView.findViewById(R.id.tv_order_icon));
            viewHolder.tvOrderDateTime		    = ((TextView)convertView.findViewById(R.id.tv_order_datetime));
            viewHolder.tvOrderTicketNumber	    = ((TextView)convertView.findViewById(R.id.tv_order_ticketnumber));
            viewHolder.tvOrderCustomerName      = ((TextView)convertView.findViewById(R.id.tv_order_customername));
            viewHolder.tvOrderWaitTime			= ((TextView)convertView.findViewById(R.id.tv_order_waittime));
            viewHolder.tvOrderStatus            = ((TextView)convertView.findViewById(R.id.tv_order_status));

            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        // Set the row number
        viewHolder.tvOrderRowNumber.setText(String.valueOf(position + 1 + offset));

        // Set the order type icon
        int oloType = orderHeaderBean.getOloType();
        int orderType = orderHeaderBean.getOrderType();
        long orderStationCD = orderHeaderBean.getOrderStationCD();

        if (orderType == 1) {  // TAKEOUT ORDERS
            // GrubHub
            if (oloType == 1 && orderStationCD == 999999999) {
                viewHolder.tvOrderIcon.setImageResource(R.drawable.icon_ordertype_takeout_grubhub);
            }
            // Slice
            else if (oloType == 2 && orderStationCD == 999999999) {
                viewHolder.tvOrderIcon.setImageResource(R.drawable.icon_ordertype_takeout_slice);
            }
            // DoorDash
            else if (oloType == 3 && orderStationCD == 999999999) {
                viewHolder.tvOrderIcon.setImageResource(R.drawable.icon_ordertype_takeout_doordash);
            }
            // ChowNow
            else if (oloType == 4 && orderStationCD == 999999999) {
                // order type currently unsupported
            }
            // Eat24
            else if (oloType == 5 && orderStationCD == 999999999) {
                viewHolder.tvOrderIcon.setImageResource(R.drawable.icon_ordertype_takeout_eat24);
            }
            // Takeout Web
            else if (orderStationCD == 999999999) {
                viewHolder.tvOrderIcon.setImageResource(R.drawable.icon_ordertype_takeout_web);
            }
            // Takeout
            else {
                viewHolder.tvOrderIcon.setImageResource(R.drawable.icon_ordertype_takeout);
            }
        }
        if (orderType == 2) { // DELIVERY ORDERS
            // GrubHub
            if (oloType == 1 && orderStationCD == 999999999) {
                viewHolder.tvOrderIcon.setImageResource(R.drawable.icon_ordertype_delivery_grubhub);
            }
            // Slice
            else if (oloType == 2 && orderStationCD == 999999999) {
                viewHolder.tvOrderIcon.setImageResource(R.drawable.icon_ordertype_delivery_slice);
            }
            // DoorDash
            else if (oloType == 3 && orderStationCD == 999999999) {
                viewHolder.tvOrderIcon.setImageResource(R.drawable.icon_ordertype_delivery_doordash);
            }
            // ChowNow
            else if (oloType == 4 && orderStationCD == 999999999) {
                // order type currently unsupported
            }
            // Eat24
            else if (oloType == 5 && orderStationCD == 999999999) {
                viewHolder.tvOrderIcon.setImageResource(R.drawable.icon_ordertype_delivery_eat24);
            }
            // Takeout Web
            else if (orderStationCD == 999999999) {
                viewHolder.tvOrderIcon.setImageResource(R.drawable.icon_ordertype_delivery_web);
            }
            // Takeout
            else {
                viewHolder.tvOrderIcon.setImageResource(R.drawable.icon_ordertype_delivery);
            }
        }
        if (orderType == 3) { // DINE IN ORDERS
            // Takeout Web
            if (orderStationCD == 999999999) {
                viewHolder.tvOrderIcon.setImageResource(R.drawable.icon_ordertype_dinein_web);
            }
            // Takeout
            else {
                viewHolder.tvOrderIcon.setImageResource(R.drawable.icon_ordertype_dinein);
            }
        }

        // Set the ticket number
        viewHolder.tvOrderTicketNumber.setText("Ticket #" + String.valueOf(orderHeaderBean.getTicketNumber()));

        // Set the customer name
        String guestName = orderHeaderBean.getGuestName();

        // Use automated name if KIOSK or VALUED CUSTOMER
        if (guestName.equals("KIOSK") || guestName.equals("Valued Customer")) {
            viewHolder.tvOrderCustomerName.setText(guestName);
        }
        else {
            // Create guest name if it's a TAB
            if (guestName.length() == 0) {
                viewHolder.tvOrderCustomerName.setText(R.string.valued_customer);
            }
            else {
                // Last name pattern (space followed by a non-space char)
                Pattern lastnamePattern = Pattern.compile("\\s\\S");
                Matcher matcher = lastnamePattern.matcher(guestName);

                // If there is a last name, abbreviate it
                if (matcher.find()) {
                    // Split the name at the space
                    String[] name = guestName.split(" ");
                    // Get the first name
                    String firstName = name[0];
                    // Get the first character of the last name
                    String lastInitial = Character.toString(name[1].charAt(0));

                    // Get app context and set customer name
                    //Context context = TVWaitQueueApplication.getAppContext();
                    viewHolder.tvOrderCustomerName.setText(context.getString(R.string.abbreviated_customer_name, firstName, lastInitial));
                    //viewHolder.tvOrderCustomerName.setText(name[0] + " " + name[1].charAt(0) + ".");
                }
                else { // Else just use customer name
                    viewHolder.tvOrderCustomerName.setText(guestName);
                }
            }
        }

        // Set order ready time
        long orderTimeLong = orderHeaderBean.getOrderDateAsLong();
        Date orderTime = new Date(orderTimeLong);
        DateFormat df = new SimpleDateFormat( "MM/dd/yy h:mm aa", Locale.US);
        viewHolder.tvOrderDateTime.setText(df.format(orderTime));

        // Set the ready time
        long readyTimeLong = orderHeaderBean.getOrderFulfilledDateAsLong();
        Date readyTime = new Date(readyTimeLong);
        DateFormat df2 = new SimpleDateFormat( "h:mm aa", Locale.US);
        viewHolder.tvOrderWaitTime.setText(df2.format(readyTime));

        // Set the status label to ready
        viewHolder.tvOrderStatus.setText(R.string.ready_label);
        viewHolder.tvOrderStatus.setBackgroundResource(R.drawable.rounded_corner_green);

        return convertView;
    }

    /**
     * Set list number offset for multiple pages
     * @param offset The offset amount used to display the starting order on a page
     */
    public void setOffset(int offset) {
        this.offset = offset;
    }
}
