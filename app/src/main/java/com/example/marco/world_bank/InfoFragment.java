package com.example.marco.world_bank;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class InfoFragment extends Fragment {
    MyListener activityCallback;
    public interface MyListener{
        public void onCloseButtonClick(View view);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            activityCallback = (MyListener)
                    getActivity();
            } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString() +
                    " must implement MyListener"); }
    }

}
