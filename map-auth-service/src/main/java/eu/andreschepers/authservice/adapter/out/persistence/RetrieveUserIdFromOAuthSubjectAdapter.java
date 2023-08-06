package eu.andreschepers.authservice.adapter.out.persistence;

import eu.andreschepers.authservice.application.port.out.IRetrieveUserIdFromOAuthSubjectPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
public class RetrieveUserIdFromOAuthSubjectAdapter implements IRetrieveUserIdFromOAuthSubjectPort {

    @Override
    public String retrieveUserIdFromOAuthSubject(String oAuthSubject) {
        var randomUuid = UUID.randomUUID().toString();
        log.info("Retrieved mocked user id: [{}]", randomUuid);
        return randomUuid;
    }
}
