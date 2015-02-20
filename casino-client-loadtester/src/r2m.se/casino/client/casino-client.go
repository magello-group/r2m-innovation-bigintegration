package main 

import (
    "flag"
    "fmt"
    "net/http"
    "io/ioutil"
    "time"
    "encoding/json"
    "math/rand"
)

type Message struct {
    Ip string
}

func callserver(url string, user string, callcounter chan int) { 
    //fmt.Printf("URL: %s\n", url)
	resp, err := http.Get(url)
	if err != nil {
    	fmt.Printf("Error1 %s", err)
	}
    //fmt.Printf("Responsecode %d\n", resp.StatusCode)
	defer resp.Body.Close()
	body, err := ioutil.ReadAll(resp.Body)
	if err != nil {
    	fmt.Printf("Error2 %s", err)
	}
	var m Message
    json.Unmarshal(body, &m)
    //fmt.Printf(string(body[:])+"\n")
    //fmt.Printf(m.Ip+"\n")
    //fmt.Printf(user+"\n")
    callcounter <- 1
	go callserver(url, user, callcounter)
}

var letters = []rune("abcdefghijklmnopqrstuvwxyz")

func randEmail() string {
    b := make([]rune, 18)
    for i := range b {
        b[i] = letters[rand.Intn(len(letters))]
    }
    return string(b)+"@nonsense.com"
}

var getcounter = make(chan int)

func callcounterGatherer(callcounter chan int) {
	start := time.Now()
	var counter = 0
	for {
		counter += <- callcounter
		if (time.Since(start).Seconds() >= 1) {
			fmt.Printf("Calls per second %d\n", counter)
			counter = 0
			start = time.Now()	
		}
	}
}

func main() {
	
	serverPtr := flag.String("u", "http://www.google.com/ddwwqwdwqdqw", "Server to call.")
    usercountPtr := flag.Int("c", 4, "Number of users to simulate.")
    secondsPtr := flag.Int64("s", 5, "Number of seconds to run simulation.")
    flag.Parse()
    
    fmt.Printf("Hitting '%s' with %d users\n", *serverPtr, *usercountPtr)

    var callcounter = make(chan int)
	go callcounterGatherer(callcounter)

	for i := 0; i < *usercountPtr; i++ {
      callserver(*serverPtr, randEmail(), callcounter)
	}
	time.Sleep(time.Duration(*secondsPtr) * time.Second)

}
