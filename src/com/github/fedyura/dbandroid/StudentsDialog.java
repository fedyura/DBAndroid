package com.github.fedyura.dbandroid;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

public class StudentsDialog extends DialogFragment {
	
	
	public interface StudentsDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog, Student student);
        public void onDialogNegativeClick(DialogFragment dialog);
    }
	
	private Student student;
	private EditText edTextInit, edTextTel, edTextEmail;
	private RadioButton rbButtonFirst, rbButtonSecond, rbButtonThird, rbButtonForth; 
	
	StudentsDialogListener mListener;
	
	public StudentsDialog() {
		
	}
	
	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.studinfo_dialog, null);
        
        student = getArguments().getParcelable(MainActivity.STUDENT_INFO);
        
        edTextInit = (EditText) dialogView.findViewById(R.id.stinitEdit);
        edTextInit.setText(student.getInitials());
        
        rbButtonFirst = (RadioButton) dialogView.findViewById(R.id.first);
        rbButtonSecond = (RadioButton) dialogView.findViewById(R.id.second);
        rbButtonThird = (RadioButton) dialogView.findViewById(R.id.third);
        rbButtonForth = (RadioButton) dialogView.findViewById(R.id.forth);
        
        rbButtonFirst.setChecked(false);
        rbButtonSecond.setChecked(false);
        rbButtonThird.setChecked(false);
        rbButtonForth.setChecked(false);
        
        int num_semester = student.getNumSemester();
        switch(num_semester) {
        	
        	case 1:
        		rbButtonFirst.setChecked(true);
        		break;
        	
        	case 2:
        		rbButtonSecond.setChecked(true);
        		break;
        	
        	case 3:
        		rbButtonThird.setChecked(true);
        		break;
        	
        	case 4:
        		rbButtonForth.setChecked(true);
        		break;
        	
        	default:
        		break;
        }
        
        edTextTel = (EditText) dialogView.findViewById(R.id.sttelEdit);
        edTextTel.setText(student.getTelephone());
        
        edTextEmail = (EditText) dialogView.findViewById(R.id.stemailEdit);
        edTextEmail.setText(student.getEmail());
        
        builder.setView(dialogView)
        	.setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                // sign in the user ...
            		student.setInitials(edTextInit.getText().toString());
            		
            		int numSemester = 0;
            		if (rbButtonFirst.isChecked()) numSemester = 1; 
            		else if (rbButtonSecond.isChecked()) numSemester = 2;
            		else if (rbButtonThird.isChecked()) numSemester = 3;
            		else if (rbButtonForth.isChecked()) numSemester = 4;
            		
            		student.setNumSemester(numSemester);
            		student.setTelephone(edTextTel.getText().toString());
            		student.setEmail(edTextEmail.getText().toString());
            		
            		mListener.onDialogPositiveClick(StudentsDialog.this, student);
            	}
        	})
        	.setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
               public void onClick(DialogInterface dialog, int id) {
                   mListener.onDialogNegativeClick(StudentsDialog.this);
               }
           });
        
        // Create the AlertDialog object and return it
        return builder.create();
    }
	
	@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (StudentsDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }
}
