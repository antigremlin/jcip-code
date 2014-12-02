package spike.remoting;

import java.util.concurrent.Future;

public interface RemoteApi {
    Future<String> foo(String arg);
}
