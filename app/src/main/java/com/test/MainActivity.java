package com.test;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableLayout.LayoutParams;
import android.widget.TableRow;
import android.widget.TextView;


@SuppressLint("NewApi")
public class MainActivity extends Activity {

    private TextView tvAdapter;
    private ListView lvDeviceModel;
    private TableLayout tlDeviceInfo;

    private String[] stringArrays;
    private Map<String, String> deviceParameter = new HashMap<String, String>();
    private List<TextView> textViewList = new LinkedList<TextView>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvAdapter = (TextView) findViewById(R.id.tv_adaptor);
        lvDeviceModel = (ListView) findViewById(R.id.lv_model_list);

        stringArrays = getResources().getStringArray(R.array.lenovo_model);
        lvDeviceModel.setAdapter(new DeviceInfoAdapter());

        tlDeviceInfo = (TableLayout) View.inflate(this, R.layout.list_view_item1, null);
        addDeviceInfo(getString(R.string.build), "", true);
        deviceParameter.put(getString(R.string.build), "");
        addDeviceInfo(getString(R.string.device_brand), Build.BRAND, true);
        deviceParameter.put(getString(R.string.device_brand), Build.BRAND);
        addDeviceInfo(getString(R.string.device_model), Build.MODEL, true);
        deviceParameter.put(getString(R.string.device_model), Build.MODEL);
        addDeviceInfo(getString(R.string.device_cpu_abi), Build.CPU_ABI, true);
        deviceParameter.put(getString(R.string.device_cpu_abi), Build.CPU_ABI);
        addDeviceInfo(getString(R.string.device_finger_print), Build.FINGERPRINT, true);
        deviceParameter.put(getString(R.string.device_finger_print), Build.FINGERPRINT);
        addDeviceInfo(getString(R.string.sdk_int), String.valueOf(Build.VERSION.SDK_INT), true);
        deviceParameter.put(getString(R.string.sdk_int), String.valueOf(Build.VERSION.SDK_INT));

        addDeviceInfo(getString(R.string.sdk_string), Build.VERSION.RELEASE, true);
        deviceParameter.put(getString(R.string.sdk_string), Build.VERSION.RELEASE);
        if (Build.VERSION.SDK_INT >= 9) {
            addDeviceInfo(getString(R.string.hardware_serial), Build.SERIAL, true);
            deviceParameter.put(getString(R.string.hardware_serial), Build.SERIAL);
        }

        addDeviceInfo(getString(R.string.displaymetric), "", true);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        float density = dm.density;
        int densityDpi = dm.densityDpi;
        float densityXDpi = dm.xdpi;
        float densityYDpi = dm.ydpi;
        float scaledDensity = dm.scaledDensity;
        String dpiTypeMatching = detectScreenDensityType(densityDpi);
        addDeviceInfo(getString(R.string.density_ratio), String.valueOf(density), true);
        addDeviceInfo(getString(R.string.scaled_density_ration), String.valueOf(scaledDensity), true);
        addDeviceInfo(getString(R.string.scaled_density_matching), dpiTypeMatching, true);
        addDeviceInfo(getString(R.string.screen_logic_density_dpi), String.valueOf(densityDpi), true);
        addDeviceInfo(getString(R.string.x_physical_density), String.valueOf(densityXDpi), true);
        addDeviceInfo(getString(R.string.y_physical_density), String.valueOf(densityYDpi), true);
        addDeviceInfo(getString(R.string.screen_px), String.valueOf(width) + "x" + String.valueOf(height), true);
        double widthInch = width / densityXDpi;
        double heightInch = height / densityYDpi;
        double screen_size = Math.sqrt(widthInch * widthInch + heightInch * heightInch);
        addDeviceInfo(getString(R.string.screen_size), String.valueOf(screen_size), true);

        addDeviceInfo(getString(R.string.configuration), "", true);
        Configuration cfg = getResources().getConfiguration();
        int orientation = cfg.orientation;
        addDeviceInfo(getString(R.string.screen_orientation),
                orientation == Configuration.ORIENTATION_LANDSCAPE ? getString(R.string.landscape_orientation)
                        : getString(R.string.portait_orientation), true);
        int navigation = cfg.navigation;
        addDeviceInfo(getString(R.string.navigation_type),
                navigation == Configuration.NAVIGATION_NONAV ? getString(R.string.no_navigation)
                        : cfg.navigation == Configuration.NAVIGATION_WHEEL ? getString(R.string.wheel)
                                : cfg.navigation == Configuration.NAVIGATION_DPAD ? getString(R.string.navigation_dpad)
                                        : getString(R.string.trackball), true);
        int touchscreen = cfg.touchscreen;
        addDeviceInfo(getString(R.string.touch_screen_type), touchscreen == Configuration.TOUCHSCREEN_NOTOUCH ? "无触摸屏"
                : cfg.touchscreen == Configuration.TOUCHSCREEN_STYLUS ? "触摸笔式" : "手指式", true);
        addDeviceInfo(getString(R.string.screen_size_type), getTelphoneScreenFromSys(this), true);
        if (Build.VERSION.SDK_INT >= 13) {
            int screenHeightDp = cfg.screenHeightDp;
            int screenWidthDp = cfg.screenWidthDp;
            addDeviceInfo(getString(R.string.screen_cfg_dp),
                    String.valueOf(screenWidthDp) + "x" + String.valueOf(screenHeightDp), true);
            int smallestScreenWidthDp = cfg.smallestScreenWidthDp;
            addDeviceInfo(getString(R.string.screen_smallest_width_dp), String.valueOf(smallestScreenWidthDp), true);
        }
        addDeviceInfo(getString(R.string.font_scale), String.valueOf(cfg.fontScale), true);
        int keyboard = cfg.keyboard;
        addDeviceInfo(getString(R.string.hardkeyboard_type), keyboard == Configuration.KEYBOARD_QWERTY ? "qwerty键盘"
                : keyboard == Configuration.KEYBOARD_12KEY ? "12键键盘" : "无键盘", true);
        int keyboardHidden = cfg.keyboardHidden;
        addDeviceInfo(getString(R.string.keyboard_available), keyboardHidden == Configuration.KEYBOARDHIDDEN_NO ? "No"
                : "Yes", true);
        addDeviceInfo(getString(R.string.ui_model), getUIModel(this), true);
        addDeviceInfo(getString(R.string.locale), cfg.locale.toString(), true);
        addDeviceInfo(getString(R.string.configuration), cfg.toString(), true);

        TextView deviceMatchingList = new TextView(this);
        deviceMatchingList.setText(R.string.dimens_matching_device_list);
        deviceMatchingList.setTextColor(Color.rgb(0xF0, 0xF0, 0xF0));
        deviceMatchingList.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        LayoutParams params = new LayoutParams();
        params.width = LayoutParams.WRAP_CONTENT;
        params.height = LayoutParams.WRAP_CONTENT;
        int margin = (int) (5 * density);
        params.setMargins(margin, margin, margin, margin);
        deviceMatchingList.setLayoutParams(params);
        tlDeviceInfo.addView(deviceMatchingList);
    }

    private TableRow addDeviceInfo(String key, String value, boolean hasDivison) {
        TableRow tableRow = (TableRow) View.inflate(this, R.layout.table_row_item, null);
        TextView keyView = (TextView) tableRow.findViewById(R.id.tv_row_key);
        TextView valueView = (TextView) tableRow.findViewById(R.id.tv_row_value);
        View divisionView = tableRow.findViewById(R.id.vw_division);
        keyView.setText(key);
        valueView.setText(value);
        if (TextUtils.isEmpty(value)) {
            valueView.setVisibility(View.GONE);
            divisionView.setVisibility(View.GONE);
        }
        tlDeviceInfo.addView(tableRow);
        if (hasDivison) {
            View.inflate(this, R.layout.table_row_item_division, tlDeviceInfo);
        }
        return tableRow;
    }

    /**
     * 检测屏幕的的 density 类型
     * @param densityDpi
     *          结果为 ldpi mdpi hdpi xhdpi xxhdpi xxxhdpi 中的一个
     * @return
     */
    private String detectScreenDensityType(int densityDpi) {
        String dpiTypeMatching = "";
        if (densityDpi <= 120) {
            dpiTypeMatching = "ldpi";
        } else if (densityDpi > 120 && densityDpi <= 160) {
            dpiTypeMatching = "mdpi";
        } else if (densityDpi > 160 && densityDpi <= 240) {
            dpiTypeMatching = "hdpi";
        } else if (densityDpi > 240 && densityDpi <= 320) {
            dpiTypeMatching = "xhdpi";
        } else if (densityDpi > 320 && densityDpi <= 480) {
            dpiTypeMatching = "xxhdpi";
        } else if (densityDpi > 480 && densityDpi <= 640) {
            dpiTypeMatching = "xxhdpi";
        }

//        dpiTypeMatching = getResources().getString(R.string.density_dpi);
        return dpiTypeMatching;
    }

    /**
     * 获取 UI 模式的类型
     * @param context
     * @return
     */
    public static String getUIModel(Context context) {
        Configuration config = context.getResources().getConfiguration();
        StringBuffer result = new StringBuffer();
        if ((config.uiMode & Configuration.UI_MODE_TYPE_MASK) == Configuration.UI_MODE_TYPE_DESK) {
            result.append("desk | ");
        } else if ((config.uiMode & Configuration.UI_MODE_TYPE_MASK) == Configuration.UI_MODE_TYPE_CAR) {
            result.append("car | ");
        } else if ((config.uiMode & Configuration.UI_MODE_TYPE_MASK) == Configuration.UI_MODE_TYPE_TELEVISION) {
            result.append("television | ");
        } else if ((config.uiMode & Configuration.UI_MODE_TYPE_MASK) == Configuration.UI_MODE_TYPE_APPLIANCE) {
            result.append("appliance | ");
        } else if ((config.uiMode & Configuration.UI_MODE_TYPE_MASK) == Configuration.UI_MODE_TYPE_NORMAL) {
            result.append("normal | ");
        } else if ((config.uiMode & Configuration.UI_MODE_TYPE_MASK) == Configuration.UI_MODE_TYPE_UNDEFINED) {
            result.append("未定义 | ");
        }
        if ((config.uiMode & Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES) {
            result.append(R.string.night_model);
        } else if ((config.uiMode & Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_NO) {
            result.append(R.string.light_model);
        } else if ((config.uiMode & Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_UNDEFINED) {
            result.append("未定义");
        }
        return result.toString();
    }

    /**
     * 获取屏幕大小的类型
     * @param context
     * @return
     */
    public static String getTelphoneScreenFromSys(Context context) {
        Configuration config = context.getResources().getConfiguration();
        if ((config.screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE) {
            return "xlarge";
        } else if ((config.screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE) {
            return "large";
        } else if ((config.screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_SMALL) {
            return "small";
        } else if ((config.screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_NORMAL) {
            return "normal";
        } else if ((config.screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_UNDEFINED) {
            return "normal";
        } else {
            return "normal";
        }
    }

    public class DeviceInfoAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return stringArrays.length + 1;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (position == 0) {
                if (convertView instanceof TextView) {
                    textViewList.add((TextView) convertView);
                }
                convertView = tlDeviceInfo;
            } else {
                if (convertView == null || !(convertView instanceof TextView)) {
                    TextView textView = null;
                    if (textViewList.size() > 0) {
                        textView = textViewList.remove(0);
                    }
                    if (textView == null) {
                        convertView = View.inflate(MainActivity.this, R.layout.list_view_item2, null);
                    } else {
                        convertView = textView;
                    }
                }
                ((TextView) convertView).setText(stringArrays[position - 1]);
            }
            return convertView;
        }
    }
}
