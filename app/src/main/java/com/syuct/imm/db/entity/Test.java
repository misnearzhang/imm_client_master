/*
package com.syuct.imm.db.entity;

import java.io.IOException;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class Test {
	public static void main(String[] args) {
		Test test = new Test();
		Schema s = new Schema(1, "com.syuct.imm.db.entity");
		s.setDefaultJavaPackageDao("com.syuct.imm.db.entity.dao");
		s.setDefaultJavaPackageTest("com.syuct.imm.db.entity.test");
		test.message(s);
		test.user(s);
		try {
			new DaoGenerator().generateAll(s, ".");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	*/
/**
	 * 消息
	 * @param schema
	 *//*

	public void message(Schema schema) {
		Entity message = schema.addEntity("Message");
		message.setTableName("message");
		message.addStringProperty("Id").columnName("id").primaryKey().unique().notNull();
		message.addStringProperty("userId").columnName("user_id");
		message.addStringProperty("userName").columnName("user_name");
		message.addStringProperty("friendId").columnName("friend_id");
		message.addStringProperty("friendName").columnName("friend_name");
		message.addStringProperty("uuid").columnName("uuid");// 消息id
		message.addStringProperty("status").columnName("status");
		message.addLongProperty("addTime").columnName("add_time");
	}
	
	public void user(Schema schema) {
		Entity user = schema.addEntity("User");
		user.setTableName("message");
		user.addStringProperty("Id").columnName("id").primaryKey().unique().notNull();
		user.addStringProperty("userId").columnName("user_id");
		user.addStringProperty("userName").columnName("user_name");
		user.addStringProperty("password").columnName("password");
		user.addStringProperty("status").columnName("status");
		user.addDateProperty("addTime").columnName("add_time");
	}
}
*/
