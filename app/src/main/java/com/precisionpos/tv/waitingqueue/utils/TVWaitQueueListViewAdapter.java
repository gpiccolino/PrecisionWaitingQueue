package com.precisionpos.tv.waitingqueue.utils;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.precisionpos.tv.waitingqueue.R;
import com.precisionpos.tv.waitingqueue.beans.ListOrder;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @since 10/14/22
 * @author Kate
 */
public class TVWaitQueueListViewAdapter extends BaseAdapter {


    private final LayoutInflater inflater;
    private final Context context;
    private int offset;
//    private int selectedColor;
//    private int transparentColor        = Color.parseColor("#00000000");
//    private int colorBgrdDark           = Color.parseColor("#424242");
//    private int colorBgrdGreen		    = Color.parseColor("#caf7ad");
//    private int colorTextGrey           = Color.parseColor("#a0a0a0");
//    private int colorLabelWaiting	    = Color.parseColor("#e9312b");
//    private int colorLabelReady		    = Color.parseColor("#2d9823");

    private List<ListOrder> orderList = new ArrayList<>();


    /*
     * Constructor
     * @param activity
     * @param context
     */
    public TVWaitQueueListViewAdapter(Activity activity, Context context) {
        super();
        inflater 			= (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //selectedColor 		= colorBgrdGreen;
        this.context = context;
    }

    /**
     * Our view holder
     */
    private static class ViewHolder {
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
     * @param orderList A list of POS orders
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
        return  orderList == null ? 0 : orderList.size();
    }

    /*
     * (non-Javadoc)
     *
     * @see android.widget.Adapter#getItem(int)
     */
    public Object getItem(int position) {
        return null;
    }

    /**
     *
     * @param position list position
     * @return boolean
     */
    public boolean isEnabled(int position) {
        return orderList == null || position != orderList.size();
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
     * @param position list position
     * @param convertView view
     * @param parent parent view
     * @return convertView
     */
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        // Get order header
        ListOrder orderHeaderBean = orderList.get(position);

        if (convertView == null || convertView.getTag() == null) {
            convertView = inflater.inflate(R.layout.activity_tv_order_row, null);

            viewHolder = new ViewHolder();
            viewHolder.tvOrderRowNumber			= convertView.findViewById(R.id.tv_order_row_number);
            viewHolder.tvOrderIcon		        = convertView.findViewById(R.id.tv_order_icon);
            viewHolder.tvOrderDateTime		    = convertView.findViewById(R.id.tv_order_datetime);
            viewHolder.tvOrderTicketNumber	    = convertView.findViewById(R.id.tv_order_ticketnumber);
            viewHolder.tvOrderCustomerName      = convertView.findViewById(R.id.tv_order_customername);
            viewHolder.tvOrderWaitTime			= convertView.findViewById(R.id.tv_order_waittime);
            viewHolder.tvOrderStatus            = convertView.findViewById(R.id.tv_order_status);

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
        String ticketNumber = String.valueOf(orderHeaderBean.getTicketNumber());
        viewHolder.tvOrderTicketNumber.setText(context.getString(R.string.ticket_number, ticketNumber));
        //viewHolder.tvOrderTicketNumber.setText("Ticket #" + String.valueOf(orderHeaderBean.getTicketNumber()));

        // Set the customer name
        String guestName = orderHeaderBean.getGuestName();

        // Use automated name if KIOSK or VALUED CUSTOMER
        if (guestName.equals("KIOSK") || guestName.equals("Valued Customer")) {
            viewHolder.tvOrderCustomerName.setText(guestName);
        }
        else {
            // If it's a TAB, set the customer name to "Valued Customer"
            if (guestName.length() == 0) {
                viewHolder.tvOrderCustomerName.setText(context.getString(R.string.valued_customer));
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

                    // Set customer name
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
        DateFormat df = new SimpleDateFormat( "MM/dd/yy h:mm aa");
        viewHolder.tvOrderDateTime.setText(df.format(orderTime));

        // Set the arrival time
        long arrivalTimeLong = orderHeaderBean.getWaitingTimeStart();
        Date arrivalTime = new Date(arrivalTimeLong);
        DateFormat df2 = new SimpleDateFormat( "h:mm aa");
        viewHolder.tvOrderWaitTime.setText(df2.format(arrivalTime));

        // Set the status label
        viewHolder.tvOrderStatus.setText("Waiting");

        return convertView;
    }

    /**
     * Set list number offset for multiple pages
     * @param offset
     */
    public void setOffset(int offset) {
        this.offset = offset;
    }
}
