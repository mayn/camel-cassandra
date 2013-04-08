[![Build Status](https://travis-ci.org/mayn/camel-cassandra.png?branch=master)](https://travis-ci.org/mayn/camel-cassandra)

camel-cassandra An Apache Camel component for integrating with Apache Cassandra
Forked from (https://github.com/ticktock/camel-cassandra), modeled after existing camel components


This component doesn't currently support being used as a consumer endpoint. Meaning it cannot be used as a from().

URI format
----------
<pre>
cassandra://hostname[:port]/[?options]
</pre>
Port is optional and if not specified then defaults to 9160.
You can append query options to the URI in the following format, ?options=value&option2=value&...

URI Options
-----------
<table>
  <tr>
    <th>Name</th><th>Default Value</th><th>Context</th><th>Description</th>
  </tr>
  <tr>
    <td>consistencyLevel</td><td>QUORUM</td><td></td><td></td>
  </tr>
  <tr>
    <td>keyspace</td><td>null</td><td></td><td></td>
  </tr>
  <tr>
    <td>operation</td><td>null</td><td></td><td></td>
  </tr>
</table>
