import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


void main() throws Exception {
    String url = "https://transport.integration.sl.se/v1/sites/9301/departures?transport=METRO&forecast=60";
    //9301 för husby
    HttpResponse<String> response;
    try (HttpClient client = HttpClient.newHttpClient()) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
        response = client.send(request, HttpResponse.BodyHandlers.ofString());
    }
    String[] trains = response.body().split("\\{");
    ArrayList<String> infoIcareabout = new ArrayList<>();
    for (String t : trains)
        if (t.contains("destination")) {
            infoIcareabout.add(t);
        }
    //infoIcareabout.forEach(System.out::println);
    for (String info : infoIcareabout) {
        String[] bestinfo = info.split(",");
        if (bestinfo[4].contains("Nu")) {
            IO.println(bestinfo[0].substring(16, bestinfo[0].length() - 1) + " avgår nu");
        } else if (bestinfo[3].contains("CANCELLED")) {
            IO.println(bestinfo[0].substring(16, bestinfo[0].length() - 1) + " " + bestinfo[4].substring(12, bestinfo[4].length() - 1) + " är INSTÄLLD!");
        } else {
            IO.println(bestinfo[0].substring(16, bestinfo[0].length() - 1) + " " + bestinfo[4].substring(12, bestinfo[4].length() - 1));
        }
    }
}
