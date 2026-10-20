package app.security;

import java.util.Set;

public interface ISecurityUser {

    Set<String> getRolesAsStrings();

    boolean verifyPassword(String password);
}