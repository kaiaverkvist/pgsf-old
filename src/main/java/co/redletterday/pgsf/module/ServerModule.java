package co.redletterday.pgsf.module;

import co.redletterday.pgsf.PgsfServer;

public interface ServerModule {
    void onInit(PgsfServer server);
    void onDestroy();
}
