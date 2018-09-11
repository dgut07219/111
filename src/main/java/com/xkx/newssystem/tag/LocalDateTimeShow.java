package com.xkx.newssystem.tag;
import java.io.IOException;
import java.time.LocalDateTime;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class LocalDateTimeShow extends SimpleTagSupport{
	private LocalDateTime dateTime;
	private String type;
	public void doTag() throws JspException,IOException{
		String year=String.valueOf(dateTime.getYear());
		String month= dateTime.getMonthValue()>9 ? String.valueOf(dateTime.getMonthValue()):"0"+dateTime.getMonthValue();
		String day= dateTime.getDayOfMonth()>9 ? String.valueOf(dateTime.getDayOfMonth()):"0"+dateTime.getDayOfMonth();
		String hour= dateTime.getHour()>9 ? String.valueOf(dateTime.getHour()):"0"+dateTime.getHour();
		String minute= dateTime.getMinute()>9 ? String.valueOf(dateTime.getMinute()):"0"+dateTime.getMinute();
		String second= dateTime.getSecond()>9 ? String.valueOf(dateTime.getSecond()):"0"+dateTime.getSecond();
		
		if(type!=null ){
			if(type.equals("YMD")){
				this.getJspContext().getOut().write(year+"年"+month+"月"+day+"日");
			}else if(type.equals("YM")){
				this.getJspContext().getOut().write(year+"年"+month+"月");
			}else if(type.equals("YMDHM")){
				this.getJspContext().getOut().write(year+"年"+month+"月"+day+"日"
						+hour+"时"+minute+"分");
			}else if(type.equals("YMDHMS")){
				this.getJspContext().getOut().write(year+"年"+month+"月"+day+"日"
						+hour+"时"+minute+"分"+second+"秒");
			}				
		}
	}
	public LocalDateTime getDateTime() {
		return dateTime;
	}
	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
