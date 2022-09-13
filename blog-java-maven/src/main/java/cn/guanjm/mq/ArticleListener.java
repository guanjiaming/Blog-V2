package cn.guanjm.mq;

import cn.guanjm.service.PageService;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ArticleListener {

    @Autowired
    private PageService pageService;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "um.article.queue", durable = "true"),
            exchange = @Exchange(value = "um.article.exchange", type = ExchangeTypes.TOPIC),
            key = {"item.update", "item.insert"}
    ))
    public void listenerInterOrUpdate(Long id) {
        System.out.println("RabbitListener: update, insert");
        if(id == null) return;
        pageService.createHtml(id);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "um.article.queue", durable = "true"),
            exchange = @Exchange(value = "um.article.exchange", type = ExchangeTypes.TOPIC),
            key = {"item.delete"}
    ))
    public void listenerDelete(Long id) {
        if(id == null) return;
        pageService.deleteHtml(id);
    }
}
