package com.davies.questlist.ui;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.davies.questlist.R;
import com.davies.questlist.db.User;
import com.davies.questlist.db.UserHelper;

/**
 * A simple {@link Fragment} subclass. Activities that contain this fragment
 * must implement the {@link NewUserFragment.OnFragmentInteractionListener}
 * interface to handle interaction events. Use the
 * {@link NewUserFragment#newInstance} factory method to create an instance of
 * this fragment.
 * 
 */
public class NewUserFragment extends Fragment implements OnClickListener{
	/**
	 * Use this factory method to create a new instance of this fragment using
	 * the provided parameters.
	 * 
	 * @param param1
	 *            Parameter 1.
	 * @param param2
	 *            Parameter 2.
	 * @return A new instance of fragment NewUserFragment.
	 */
	public static NewUserFragment newInstance() {
		NewUserFragment fragment = new NewUserFragment();
		return fragment;
	}

	public NewUserFragment() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View rootView = inflater.inflate(R.layout.fragment_new_user, container, false);
		
		Button btnContinue = (Button) rootView.findViewById(R.id.btnContinue);
		btnContinue.setOnClickListener(this);
		
		return rootView;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public void onDetach() {
		super.onDetach();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		TextView txtUsername = (TextView) getActivity().findViewById(R.id.txtNewUsername);
		String name = txtUsername.getText().toString();
		
		if (name.length() != 0){
			//Store the name
			User user = new User(name);
			
			UserHelper uh = new UserHelper(getActivity());
			user.setId(uh.createUser(user));
			
			NavigationDrawerFragment mNavigationDrawerFragment = (NavigationDrawerFragment) getActivity().getSupportFragmentManager()
					.findFragmentById(R.id.navigation_drawer);
			
			mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
					(DrawerLayout) getActivity().findViewById(R.id.drawer_layout));
			
			mNavigationDrawerFragment.userChanged(user);
			
			getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
		}
	}

}
