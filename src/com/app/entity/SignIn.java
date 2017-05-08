package com.app.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户签到表（每次签到 会有不同的随机积分 送）
 * 
 * @author admin
 * @date 2015-5-22
 */
public class SignIn implements Serializable{
	public String signId;// 签到表Id
	public Date signDate;// 签到时间 如 2015-08-10格式
	public Integer integral;// 签到获取的随机积分
	public String status;// 签到状态（不能重复签到）
    public SignIn(String signId,Date signDate,Integer integral,String status)
    {
    	this.signId=signId;
    	this.signDate=signDate;
    	this.integral=integral;
    	this.status=status;
    }
	public String getSignId() {
		return signId;
	}

	public void setSignId(String signId) {
		this.signId = signId;
	}

	public Date getSignDate() {
		return signDate;
	}

	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}

	public Integer getIntegral() {
		return integral;
	}

	public void setIntegral(Integer integral) {
		this.integral = integral;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
