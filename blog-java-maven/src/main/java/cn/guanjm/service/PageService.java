package cn.guanjm.service;

import java.util.Map;

public interface PageService {
    Map<String, Object> loadModel(Long id);

    void createHtml(Long id);

    void deleteHtml(Long id);
}
