package com.syuct.imm.db.entity.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.syuct.imm.db.entity.Message;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "MESSAGE".
*/
public class MessageDao extends AbstractDao<Message, String> {

    public static final String TABLENAME = "MESSAGE";

    /**
     * Properties of entity Message.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Uuid = new Property(0, String.class, "uuid", true, "UUID");
        public final static Property UserAccount = new Property(1, String.class, "userAccount", false, "USER_ACCOUNT");
        public final static Property FriendId = new Property(2, String.class, "friendId", false, "FRIEND_ID");
        public final static Property FriendAccount = new Property(3, String.class, "friendAccount", false, "FRIEND_ACCOUNT");
        public final static Property Status = new Property(4, String.class, "status", false, "STATUS");
        public final static Property Type = new Property(5, String.class, "type", false, "TYPE");
        public final static Property ImageUri = new Property(6, String.class, "imageUri", false, "IMAGE_URI");
        public final static Property ImageUriLocal = new Property(7, String.class, "imageUriLocal", false, "IMAGE_URI_LOCAL");
        public final static Property AddTime = new Property(8, Long.class, "addTime", false, "ADD_TIME");
    }


    public MessageDao(DaoConfig config) {
        super(config);
    }
    
    public MessageDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"MESSAGE\" (" + //
                "\"UUID\" TEXT PRIMARY KEY NOT NULL ," + // 0: uuid
                "\"USER_ACCOUNT\" TEXT," + // 1: userAccount
                "\"FRIEND_ID\" TEXT," + // 2: friendId
                "\"FRIEND_ACCOUNT\" TEXT," + // 3: friendAccount
                "\"STATUS\" TEXT," + // 4: status
                "\"TYPE\" TEXT," + // 5: type
                "\"IMAGE_URI\" TEXT," + // 6: imageUri
                "\"IMAGE_URI_LOCAL\" TEXT," + // 7: imageUriLocal
                "\"ADD_TIME\" INTEGER);"); // 8: addTime
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"MESSAGE\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Message entity) {
        stmt.clearBindings();
 
        String uuid = entity.getUuid();
        if (uuid != null) {
            stmt.bindString(1, uuid);
        }
 
        String userAccount = entity.getUserAccount();
        if (userAccount != null) {
            stmt.bindString(2, userAccount);
        }
 
        String friendId = entity.getFriendId();
        if (friendId != null) {
            stmt.bindString(3, friendId);
        }
 
        String friendAccount = entity.getFriendAccount();
        if (friendAccount != null) {
            stmt.bindString(4, friendAccount);
        }
 
        String status = entity.getStatus();
        if (status != null) {
            stmt.bindString(5, status);
        }
 
        String type = entity.getType();
        if (type != null) {
            stmt.bindString(6, type);
        }
 
        String imageUri = entity.getImageUri();
        if (imageUri != null) {
            stmt.bindString(7, imageUri);
        }
 
        String imageUriLocal = entity.getImageUriLocal();
        if (imageUriLocal != null) {
            stmt.bindString(8, imageUriLocal);
        }
 
        Long addTime = entity.getAddTime();
        if (addTime != null) {
            stmt.bindLong(9, addTime);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Message entity) {
        stmt.clearBindings();
 
        String uuid = entity.getUuid();
        if (uuid != null) {
            stmt.bindString(1, uuid);
        }
 
        String userAccount = entity.getUserAccount();
        if (userAccount != null) {
            stmt.bindString(2, userAccount);
        }
 
        String friendId = entity.getFriendId();
        if (friendId != null) {
            stmt.bindString(3, friendId);
        }
 
        String friendAccount = entity.getFriendAccount();
        if (friendAccount != null) {
            stmt.bindString(4, friendAccount);
        }
 
        String status = entity.getStatus();
        if (status != null) {
            stmt.bindString(5, status);
        }
 
        String type = entity.getType();
        if (type != null) {
            stmt.bindString(6, type);
        }
 
        String imageUri = entity.getImageUri();
        if (imageUri != null) {
            stmt.bindString(7, imageUri);
        }
 
        String imageUriLocal = entity.getImageUriLocal();
        if (imageUriLocal != null) {
            stmt.bindString(8, imageUriLocal);
        }
 
        Long addTime = entity.getAddTime();
        if (addTime != null) {
            stmt.bindLong(9, addTime);
        }
    }

    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0);
    }    

    @Override
    public Message readEntity(Cursor cursor, int offset) {
        Message entity = new Message( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // uuid
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // userAccount
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // friendId
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // friendAccount
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // status
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // type
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // imageUri
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // imageUriLocal
            cursor.isNull(offset + 8) ? null : cursor.getLong(offset + 8) // addTime
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Message entity, int offset) {
        entity.setUuid(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setUserAccount(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setFriendId(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setFriendAccount(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setStatus(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setType(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setImageUri(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setImageUriLocal(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setAddTime(cursor.isNull(offset + 8) ? null : cursor.getLong(offset + 8));
     }
    
    @Override
    protected final String updateKeyAfterInsert(Message entity, long rowId) {
        return entity.getUuid();
    }
    
    @Override
    public String getKey(Message entity) {
        if(entity != null) {
            return entity.getUuid();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(Message entity) {
        return entity.getUuid() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
