package com.allits.escorttracker;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.facebook.Session;

import com.allits.escorttracker.application.SlidingMenuApplication;
import com.allits.escorttracker.dispatcher.RemoveUserDispatcher;
import com.allits.escorttracker.user.UserHolder;

public class PagesFragment extends Fragment implements View.OnClickListener {

    public PagesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_pages, container, false);
        Button logoutButton = (Button) rootView.findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.logoutButton:
                onClickLogout(v);
                break;
        }
    }

    @SuppressWarnings("UnusedParameters")
    private void onClickLogout(final View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
        builder.setTitle(getResources().getString(R.string.Confirmation));
        builder.setMessage(getResources().getString(R.string.ReallyLogout));
        builder.setPositiveButton(getResources().getString(R.string.Yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Session session = Session.getActiveSession();
                if (session != null) {
                    if (!session.isClosed()) {
                        session.closeAndClearTokenInformation();
                    }
                } else {
                    session = new Session(getActivity());
                    Session.setActiveSession(session);
                    session.closeAndClearTokenInformation();
                }
                UserHolder.getInstance().setCurrentUser(null);
                ((SlidingMenuApplication) getActivity().getApplication()).persistUser();
                RemoveUserDispatcher.getInstance().userRemoved();
            }
        });
        builder.setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //No pressed, nothing to do here
            }
        });
        builder.show();
    }

}
