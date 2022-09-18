package com.fan.cloud.notifier;

import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceStatusChangedEvent;
import de.codecentric.boot.admin.server.notify.AbstractEventNotifier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * <h1>自定义服务异常告警</h1>
 */
@Slf4j
@Component
public class ServerExceptionNotifier extends AbstractEventNotifier {

    protected ServerExceptionNotifier(InstanceRepository repository) {
        super(repository);
    }

    /**
     * 实现对事件的通知
     *
     * @param event 事件
     * @param instance 服务实例
     * @return
     */
    @Override
    protected Mono<Void> doNotify(InstanceEvent event, Instance instance) {
        return Mono.fromRunnable(() -> {
            if (event instanceof InstanceStatusChangedEvent) {
                log.info("Instance Status Change: [{}],[{}],[{}]",
                        instance.getRegistration().getName(),
                        event.getInstance(),
                        ((InstanceStatusChangedEvent) event).getStatusInfo().getStatus());
                //此处可以去实现发短信，接入钉钉发消息等
            } else {
                log.info("Instance Info: [{}],[{}],[{}]",
                        instance.getRegistration().getName(),
                        event.getInstance(),
                        event.getType());
            }
        });
    }
}
