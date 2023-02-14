package com.dyledu.handler.MpHandler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.dyledu.utils.UserThreadLocal;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
public class MpAutoFillHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        Long userId =null;
        try{
            userId =Long.valueOf(UserThreadLocal.getCurrentUser());
        }catch (Exception e){
           e.printStackTrace();
//           -1表示由用户本人创建的
           userId = -1L;
        }
        this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now()); // 起始版本 3.3.0(推荐使用)
        this.strictInsertFill(metaObject, "createBy", Long.class, userId);
        this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now()); // 起始版本 3.3.0(推荐)
        this.strictUpdateFill(metaObject, "updateBy", Long.class, userId);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Long userId =Long.valueOf(UserThreadLocal.getCurrentUser());
        this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now()); // 起始版本 3.3.0(推荐)
        this.strictUpdateFill(metaObject, "updateBy", Long.class, userId);
    }
}
