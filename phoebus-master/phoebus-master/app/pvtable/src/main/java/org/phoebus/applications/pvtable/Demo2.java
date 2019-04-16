package org.phoebus.applications.pvtable;

import org.phoebus.applications.pvtable.PVTableInstance;
import org.phoebus.framework.spi.AppDescriptor;
import org.phoebus.framework.spi.AppInstance;

/**
 * @author ScXin
 * @date 2/21/2019 2:43 PM
 */
public class Demo2 {
    AppDescriptor app=new AppDescriptor() {
        @Override
        public String getName() {
            return null;
        }

        @Override
        public AppInstance create() {
            return null;
        }
    };
    PVTableInstance ps=new PVTableInstance(app);

}
