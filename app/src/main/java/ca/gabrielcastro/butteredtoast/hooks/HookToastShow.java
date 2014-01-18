/*
 * This file is part of ButteredToast.
 *
 * Copyright 2013-2014 Gabriel Castro (c)
 *
 *     ButteredToast is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     ButteredToast is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with ButteredToast.  If not, see <http://www.gnu.org/licenses/>.
 */

package ca.gabrielcastro.butteredtoast.hooks;

import android.text.SpannableStringBuilder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ca.gabrielcastro.butteredtoast.Util;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class HookToastShow extends XC_MethodHook implements AutoHookable {

    private final XC_LoadPackage.LoadPackageParam mParam;

    public HookToastShow(XC_LoadPackage.LoadPackageParam packageParam) {
        this.mParam = packageParam;
    }

    @Override
    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
        Toast t = (Toast) param.thisObject;
        try {
            View view = t.getView();
            List<TextView> list = new ArrayList<TextView>();
            if (view instanceof TextView) {
                list.add((TextView) view);
            } else if (view instanceof ViewGroup) {
                Util.finaAllTextView(list, (ViewGroup) view);
            }
            if (list.size() != 1) {
                throw new RuntimeException("number of TextViews in toast is not 1");
            }
            TextView text = list.get(0);
            CharSequence cs = text.getText();
            if (cs != null) {
                CharSequence name = text.getContext().getPackageManager().getApplicationLabel(mParam.appInfo);
                SpannableStringBuilder builder = new SpannableStringBuilder(name);
                builder.append(":\n").append(cs);
                text.setText(builder.toString());
            }
        } catch (RuntimeException e) {
            XposedBridge.log(e);
        }
    }

    @Override
    public Unhook hook() {
        return XposedHelpers.findAndHookMethod(Toast.class, "show", this);
    }
}