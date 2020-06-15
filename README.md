# Blue Matador Metric Client

**Send StatsD-style custom metrics to your Blue Matador dashboard** 

## Setup

The Blue Matador Metric Client is distributed through maven. Start by adding the following configuration to your pom.xml

```
<dependency>
    <groupId>com.bluematador</groupId>
    <artifactId>blue-matadador-client</artifactId>
    <version>0.0.1</version>
</dependency>
```

To start using the Blue Matador Metric Client, import the `BlueMatadorClient` class and the `BlueMatadorClientBuilder`
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

Once you have an instance of the Blue Matador Metric Client in your code you can start sending custom metrics. 

### Gauge
`gauge(name, value, [sampleRate], [labels])`
  * `Name: (required)` The metric name e.g. 'myapp.request.size'. Cannot contain '#' or '|'
  * `Value: (required)` The latest value to set for the metric
  * `sampleRate: (optional)` sends only a sample of data e.g. 0.5 indicates 50% of data being sent. Default value is 1
  * `labels: (optional)`  adds metadata to a metric. Can be specified as an array of strings with key-value pairs formatted with a colon separator e.g. ['account:12345']. Cannot contain '#' or '|'

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
# gauge 100
Client.gauge("request.size", 100);

# gauge 100 but sample 50%
Client.gauge("request.size", 100, 0.5);

# gauge 100 with labels
Client.gauge("request.size", 100, new String[]{ "environment:Prod", "api" });

# gauge 100, sample 50%, and send labels
Client.gauge("request.size", 100, 0.5, new String[]{ "environment:Prod", "api" });

```

### Count
`count(name, [value], [sampleRate], [labels])`
  * `Name: (required)` The metric name e.g. 'myapp.request.size'. Cannot contain ':' or '|'
  * `Value: (optional)` the amount to increment the metric by, the default is 1. 
  * `sampleRate: (optional)` sends only a sample of data e.g. 0.5 indicates 50% of data being sent. Default value is 1
  * `labels: (optional)`  adds metadata to a metric. Can be specified as an array of strings with key-value pairs formatted with a colon separator e.g. ['account:12345']. Cannot contain '#' or '|'

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
# count 1
Client.count("homepage.clicks");

# count 2
Client.count("hompage.clicks", 2);

# count 1 but sample 50%
Client.count("homepage.clicks", 1, 0.5);

# count 2 and send labels
Client.count("homepage.clicks", 2, new String[]{ "environment:Prod", "homepage" });

# count 2, sample 50%, and send labels
Client.count("homepage.clicks", 2, 0.5, new String[]{ "environment:Prod", "homepage" });

```

### Close

The close method should be called when shutting down your app.

```
Client.close();
```


# License

More details in [LICENSE.](https://github.com/bluematador/bluematador-metrics-client-java/blob/master/LICENSE)

Copyright (c) 2020 [Blue Matador, Inc.](https://www.bluematador.com/)

