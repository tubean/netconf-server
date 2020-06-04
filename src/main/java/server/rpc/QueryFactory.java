package server.rpc;

import server.IQuery;

/**
 * Factory class to create different netconf queries. It allows to create queries for the base standard
 *
 * @author carlos
 */
public class QueryFactory {

    public static Query newGetConfig(String source, String filter, String attrFilter) {
        Query query = new Query();
        query.setOperation(Operation.GET_CONFIG);
        query.setSource(source);
        query.setFilter(filter);
        query.setFilterType(attrFilter);
        return query;
    }

    public static Query newGet(String filter) {
        Query query = new Query();
        query.setOperation(Operation.GET);
        query.setFilter(filter);

        return query;
    }

    public static Query newEditConfig(String target,
                                      String defaultOperation,
                                      String testOption,
                                      String errorOption,
                                      String config) {
        Query query = new Query();
        query.setOperation(Operation.EDIT_CONFIG);
        query.setTarget(target);
        query.setDefaultOperation(defaultOperation);
        query.setTestOption(testOption);
        query.setErrorOption(errorOption);
        query.setConfig(config);

        return query;
    }

    public static Query newCloseSession() {
        Query query = new Query();
        query.setOperation(Operation.CLOSE_SESSION);

        return query;
    }

    public static Query newCopyConfig(String target, String source) {
        Query query = new Query();
        query.setOperation(Operation.COPY_CONFIG);
        query.setTarget(target);
        query.setSource(source);

        return query;
    }

    public static Query newDeleteConfig(String target) {
        Query query = new Query();
        query.setOperation(Operation.DELETE_CONFIG);
        query.setTarget(target);

        return query;
    }

    public static Query newKillSession() {
        Query query = new Query();
        query.setOperation(Operation.KILL_SESSION);

        return query;
    }

    public static Query newLock(String target) {
        Query query = new Query();
        query.setOperation(Operation.LOCK);
        query.setTarget(target);

        return query;
    }

    public static Query newKeepAlive() {

        Query query = new Query();
        query.setOperation(Operation.GET);
        query.setFilter("");
        query.setFilterType("subtree");

        return query;
    }

    public static Query newUnlock(String target) {
        Query query = new Query();
        query.setOperation(Operation.UNLOCK);
        query.setTarget(target);

        return query;
    }

    public static Query newCommit() {
        Query query = new Query();
        query.setOperation(Operation.COMMIT);

        return query;
    }

    /* Extra queries */

    public static Query newSetLogicalRouter(String idLogicalRouter) {
        Query query = new Query();
        query.setOperation(Operation.SET_LOGICAL_ROUTER);
        query.setIdLogicalRouter(idLogicalRouter);
        return query;
    }

    public static Query newGetRouteInformation() {
        Query query = new Query();
        query.setOperation(Operation.GET_ROUTE_INFO);
        return query;
    }

    public static Query newGetInterfaceInformation() {
        Query query = new Query();
        query.setOperation(Operation.GET_INTERFACE_INFO);
        return query;
    }

    public static Query newGetSoftwareInformation() {
        Query query = new Query();
        query.setOperation(Operation.GET_SOFTWARE_INFO);
        return query;
    }

    public static Query newGetRollbackInformation(String rollback) {
        Query query = new Query();
        query.setOperation(Operation.GET_ROLLBACK_INFO);
        query.setRollback(rollback);
        return query;
    }

    public static Query newValidate(String source) {
        Query query = new Query();
        query.setOperation(Operation.VALIDATE);
        query.setSource(source);
        return query;
    }

    public static Query newOpenConfiguration() {
        return newOpenConfiguration("private");
    }

    public static Query newOpenConfiguration(String scope) {
        Query query = new Query();
        query.setOperation(Operation.OPEN_CONFIG);
        query.setBody("<" + scope + "/>");
        return query;
    }

    public static Query newCloseConfiguration(String scope) {
        Query query = new Query();
        query.setOperation(Operation.CLOSE_CONFIG);
        return query;
    }

    public static Query newDiscardChanges() {
        Query query = new Query();
        query.setOperation(Operation.DISCARD);
        return query;
    }

    public static IQuery newLoadConfiguration(String url, LoadConfigurationQuery.Action action, LoadConfigurationQuery.Format format, String config) {
        return new LoadConfigurationQuery(url, action, format, config);
    }
}