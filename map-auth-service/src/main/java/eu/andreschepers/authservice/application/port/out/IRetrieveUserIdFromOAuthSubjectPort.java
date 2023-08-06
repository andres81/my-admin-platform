package eu.andreschepers.authservice.application.port.out;

public interface IRetrieveUserIdFromOAuthSubjectPort {

    String retrieveUserIdFromOAuthSubject(String oAuthSubject);
}
