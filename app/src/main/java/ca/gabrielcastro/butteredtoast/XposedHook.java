package ca.gabrielcastro.butteredtoast;

import android.text.SpannableStringBuilder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class XposedHook  implements IXposedHookLoadPackage {


    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
        XposedHelpers.findAndHookMethod(Toast.class, "show", new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                Toast t = (Toast) param.thisObject;
                try {
                    View view  = t.getView();
                    List<TextView> list = new ArrayList<TextView>();
                    if (view instanceof TextView) {
                        list.add((TextView) view);
                    } else if (view instanceof ViewGroup) {
                        finaAllTextView(list, (ViewGroup) view);
                    }
                    if (list.size() != 1) {
                        throw new RuntimeException("number of TextViews in toast is not 1");
                    }
                    TextView text = list.get(0);
                    CharSequence cs = text.getText();
                    if (cs != null) {
                        CharSequence name = text.getContext().getPackageManager().getApplicationLabel(loadPackageParam.appInfo);
                        SpannableStringBuilder builder = new SpannableStringBuilder(name);
                        builder.append(":\n").append(cs);
                        text.setText(builder.toString());
                    }
                } catch (RuntimeException e) {
                    XposedBridge.log(e);
                }
            }
        });
    }

    private void finaAllTextView(List<TextView> addTo, ViewGroup view) {
        int count = view.getChildCount();
        for (int i = 0; i < count; ++i) {
            View child = view.getChildAt(i);
            if (child instanceof TextView) {
                addTo.add((TextView) child);
            } else if (child instanceof ViewGroup) {
                finaAllTextView(addTo, view);
            }
        }
    }
}
