# Blue Matador Metric Client

**Send StatsD-style custom metrics to your Blue Matador dashboard** 

## Setup

To start using the Blue Matador metric client, import the `BlueMatadorClient` class and the `BlueMatadorClientBuilder`
class. The `BlueMatadorClientBuilder` exposes methods that allows customization to the `BlueMatadorClient` on build.

 * `.withPrefix(String metricPrefix)` The withPrefix method takes a string that will be prepended to the name of every metric you send. 

 * `.withPort(Int port)` The withPort method allows you to set the connection port for the Blue Matador Client. 

 * `.withHost(String host)` The withHost method allows you to set the connection host for the Blue Matador Client.

**Note:** The builder will detect if you have set `BLUEMATADOR_AGENT_HOST` and `BLUEMATADOR_AGENT_PORT` in the config file for your agent. If these variables have been set there is no need to manually set the host or port as they will be overridden.  

```
import com.bluematador.BlueMatadorClient;
import com.bluematador.BlueMatadorClientBuilder;

public class BlueMatadorMetricClient {

    public static void main(String[] args) throws Exception {

        BlueMatadorClient Client = new BlueMatadorClientBuilder()
            .withPrefix("app")
            .build();

    }
}

```

Once you have an instance of the Blue Matador metric client in your code you can start sending custom metrics. 

### Gauge
`gauge(name, value, [sampleRate], [tags])`
  * `Name: (required)` The metric name e.g. 'myapp.request.size'. Cannot contain '#' or '|'
  * `Value: (required)` The latest value to set for the metric
  * `sampleRate: (optional)` sends only a sample of data e.g. 0.5 indicates 50% of data being sent. Default value is 1
  * `tags: (optional)`  adds metadata to a metric. Can be specified as an array of strings with key-value pairs formatted with a colon separator e.g. ['account:12345']. Cannot contain '#' or '|'

```
import com.bluematador.BlueMatadorClient;
import com.bluematador.BlueMatadorClientBuilder;

public class BlueMatadorMetricClient {

    public static void main(String[] args) throws Exception {

        BlueMatadorClient Client = new BlueMatadorClientBuilder()
            .withPrefix("app")
            .build();

            Client.gauge("request.size", 25, 1, new String[]{"env:dev"});

    }
}

```

The following are all valid ways to send a gauge metric:

```
Client.gauge("request.size", 23.2323);

Client.gauge("request.size", 23, 1);

Client.gauge("request.size", 23, new String[]{ "environment:Prod", "account_id:1232151" });

Client.gauge("request.size", 23, 1, new String[]{ "environment:Prod", "account_id:1232151" });

```

### Count
`count(name, [value], [sampleRate], [tags])`
  * `Name: (required)` The metric name e.g. 'myapp.request.size'. Cannot contain ':' or '|'
  * `Value: (optional)` the amount to increment the metric by, the default is 1. 
  * `sampleRate: (optional)` sends only a sample of data e.g. 0.5 indicates 50% of data being sent. Default value is 1
  * `tags: (optional)`  adds metadata to a metric. Can be specified as an array of strings with key-value pairs formatted with a colon separator e.g. ['account:12345']. Cannot contain '#' or '|'

**Note:** because the count value is optional, if you want to set the sampleRate the count value must be set as well.  

```
import com.bluematador.BlueMatadorClient;
import com.bluematador.BlueMatadorClientBuilder;

public class BlueMatadorMetricClient {

    public static void main(String[] args) throws Exception {

        BlueMatadorClient Client = new BlueMatadorClientBuilder()
            .withPrefix("app")
            .build();

            Client.count("homepage.clicks", 2, .5, new String[]{"env:dev"});

    }
}

```

The following are all valid ways to send a count metric: 

```
Client.count("homepage.clicks", 23.2323);

Client.count("homepage.clicks", 23, .9);

Client.count("homepage.clicks", 23, new String[]{ "environment:Prod", "account_id:1232151" });

Client.count("homepage.clicks", 23, .7, new String[]{ "environment:Prod", "account_id:1232151" });

```

### Close

The close method should be called when shutting down your app.

```
Client.close();
```


# License

More details in [LICENSE.](https://github.com/bluematador/bluematador-metrics-client-java/blob/master/LICENSE)

Copyright (c) 2020 [Blue Matador, Inc.](https://www.bluematador.com/)

