/*
 * Copyright (C) 2018 Havoc-OS
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.settings.deviceinfo.lotus;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.android.internal.logging.nano.MetricsProto;
import com.android.settings.R;
import com.android.settings.core.instrumentation.InstrumentedDialogFragment;

public class LotusInfoDialogFragment extends InstrumentedDialogFragment {

    private static final String TAG = "LotusInfoDialog";

    private View mRootView;

    public static void show(Fragment host) {
        final FragmentManager manager = host.getChildFragmentManager();
        if (manager.findFragmentByTag(TAG) == null) {
            final LotusInfoDialogFragment dialog = new LotusInfoDialogFragment();
            dialog.show(manager, TAG);
        }
    }

    @Override
    public int getMetricsCategory() {
        return MetricsProto.MetricsEvent.DIALOG_FIRMWARE_VERSION;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.lotus_title)
                .setPositiveButton(android.R.string.ok, null /* listener */);

        mRootView = LayoutInflater.from(getActivity()).inflate(
                R.layout.dialog_lotus_info, null /* parent */);

        initializeControllers();

        return builder.setView(mRootView).create();
    }

    public void setText(int viewId, CharSequence text) {
        final TextView view = mRootView.findViewById(viewId);
        if (view != null) {
            view.setText(text);
        }
    }

    public void removeSettingFromScreen(int viewId) {
        final View view = mRootView.findViewById(viewId);
        if (view != null) {
            view.setVisibility(View.GONE);
        }
    }

    public void registerClickListener(int viewId, View.OnClickListener listener) {
        final View view = mRootView.findViewById(viewId);
        if (view != null) {
            view.setOnClickListener(listener);
        }
    }

    private void initializeControllers() {
        new LotusDateDialogController(this).initialize();
        new LotusDeviceDialogController(this).initialize();
        new LotusMaintainerDialogController(this).initialize();
        new LotusTypeDialogController(this).initialize();
        new LotusVersionDialogController(this).initialize();
    }
}
