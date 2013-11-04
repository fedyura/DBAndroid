package com.github.fedyura.dbandroid;

import android.os.Parcel;
import android.os.Parcelable;

public class Student implements Parcelable {

	private int mId;
	private String mInitials;
	private int mNumSemester;
	private String mTelephone;
	private String mEmail;
	
	public Student(int id, String initials, int num_semester, String telephone, String Email) {
		
		mId = id;
		mInitials = initials;
		mNumSemester = num_semester;
		mTelephone = telephone;
		mEmail = Email;
	}
	
	public int getId() {
		
		return mId;
	}
	
	public String getInitials() {
		
		return mInitials;
	}
	
	public int getNumSemester() {
		
		return mNumSemester;
	}
	
	public String getTelephone() {
		
		return mTelephone;
	}
	
	public String getEmail() {
		
		return mEmail;
	}

	public void setInitials(String initials) {
		
		mInitials = initials;
	}
	
	public void setNumSemester(int numSemester) {
		
		mNumSemester = numSemester;
	}
	
	public void setTelephone(String telephone) {
		
		mTelephone = telephone;
	}
	
	public void setEmail(String email) {
		
		mEmail = email;
	}
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flag) {
		// TODO Auto-generated method stub
		out.writeInt(mId);
		out.writeString(mInitials);
		out.writeInt(mNumSemester);
		out.writeString(mTelephone);
		out.writeString(mEmail);
	}
	
	public static final Parcelable.Creator<Student> CREATOR = new Parcelable.Creator<Student>() {
        public Student createFromParcel(Parcel in) {
            return new Student(in);
        }

        public Student[] newArray(int size) {
            return new Student[size];
        }
    };
    
    private Student(Parcel in) {
        mId = in.readInt();
        mInitials = in.readString();
        mNumSemester = in.readInt();
        mTelephone = in.readString();
        mEmail = in.readString();
    }
}
