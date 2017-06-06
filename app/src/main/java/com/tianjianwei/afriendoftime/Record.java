
/**
 * Created by tianjianwei20 on 2017/3/11.
 */

package com.tianjianwei.afriendoftime;


public class Record {
	//private variables
	int _id;
	String _tag;
	String _content;
	int _time_minutes;
	
	// Empty constructor
	public Record(){
		
	}
	// constructor
	public Record(int id, String tag, String content, int minutes){
		this._id = id;
		this._tag = tag;
		this._content = content;
		this._time_minutes = minutes;
	}
	
	// constructor
	public Record(String tag, String content, int minutes){
		this._tag = tag;
		this._content = content;
		this._time_minutes = minutes;
	}
	// getting ID
	public int getID(){
		return this._id;
	}
	
	// setting id
	public void setID(int id){
		this._id = id;
	}
	
	// getting tag
	public String getTag(){
		return this._tag;
	}
	
	// setting tag
	public void setTag(String tag){
		this._tag = tag;
	}
	
	// getting content
	public String getContent(){
		return this._content;
	}
	
	// setting content
	public void setContent(String content) { this._content = content; }
	
	// getting time minutes
    public int getTimeMinutes() { return this._time_minutes; }

	// setting time minutes
	public void setTimeMinutes(int ts){
		this._time_minutes = ts;
	}
}
