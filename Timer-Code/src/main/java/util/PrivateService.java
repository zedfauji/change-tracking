package util;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created with IntelliJ IDEA.
 * User: gdudhwal
 * Date: 28/01/14
 * Time: 1:26 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class PrivateService implements Service {
    AtomicBoolean inited = new AtomicBoolean(false);

    public boolean isInited() {
        return inited.get();
    }

    public void init() {
        if(inited.get())
            return;
        initService();
        inited.set(true);
    }

    public void destroy() {
        if(!inited.get())
            return;
        destroyService();
        inited.set(false);
    }


    public abstract void initService();
    public abstract void destroyService();
}
