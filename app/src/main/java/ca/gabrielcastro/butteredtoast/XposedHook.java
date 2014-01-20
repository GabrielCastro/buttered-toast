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

import ca.gabrielcastro.butteredtoast.hooks.HookToastShow;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.XC_MethodHook;

public class XposedHook implements IXposedHookZygoteInit {

    @Override
    public void initZygote(StartupParam startupParam) throws Throwable {
        XC_MethodHook.Unhook show = new HookToastShow().hook();
    }

}
