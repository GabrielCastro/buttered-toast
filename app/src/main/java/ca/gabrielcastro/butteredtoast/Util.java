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

package ca.gabrielcastro.butteredtoast;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class Util {

    private Util() {}

    public static void finaAllTextView(List<TextView> addTo, ViewGroup view) {
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
