package com.book_keeping.boo.service;

import com.book_keeping.boo.Entity.Category;
import com.book_keeping.boo.Entity.Repository.SettingsRepository;
import com.book_keeping.boo.Entity.Settings;
import com.book_keeping.boo.controller.requestEntity.TransformDataRequest;
import com.book_keeping.boo.utils.DefaultSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;

@Service
public class SettingsService {
    @Autowired
    SettingsRepository settingsRepository;

    @Autowired
    CategoryService categoryService;

    // 获取默认设置
    public Settings getDefaultSettings(Integer userid) {
        DefaultSettings defaultSettings = new DefaultSettings();
        defaultSettings.setUserid(userid);
        return updateSettings(defaultSettings);
    }

    // 更新设置
    public Settings updateSettings(Settings settings) {
        Settings settings1 = settingsRepository.findByUserId(settings.getUserid());
        if (settings1 == null) {
            return settingsRepository.save(settings);
        } else {
            settingsRepository.updateSettings(settings.getPageSize(), settings.getDefaultTimeType(), settings.getUserid());
        }
        return settings;
    }

    // 获取设置
    public Settings getSettings(Integer userid) {
        return settingsRepository.findByUserId(userid);
    }

    // 更新每页显示条数
    public Settings updatePageNum(int pageNum, Integer userid) {
           Settings settings = settingsRepository.findByUserId(userid);
            settings.setPageSize(pageNum);
            return settingsRepository.save(settings);
        }

    // 更新默认日期类型
    public Settings updateDefaultTimeType(String defaultTimeType, Integer userId) {
        Settings settings = settingsRepository.findByUserId(userId);
        settings.setDefaultTimeType(defaultTimeType);
        return settingsRepository.save(settings);
    }

    // 记录迁移

    public Settings transform(ArrayList<TransformDataRequest> list, String from, String to, Integer userId) {
        //通过类别id获取id名称
        Category category = categoryService.getNameByUserIdAndId(userId, Integer.parseInt(to));
            String to_kind_name = category.getCategoryName();
            list.forEach(item -> {
                settingsRepository.transform(Integer.parseInt(to),to_kind_name,item.getId());
            });
        return null;
    }
}
