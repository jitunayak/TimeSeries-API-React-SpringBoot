import React, { useState, useEffect } from "react";
import logo from "./logo.svg";
import "./App.css";
import axios from "axios";
import "@vaadin/vaadin-button";
import "@vaadin/vaadin-date-picker";
import "@vaadin/vaadin-time-picker";

function App() {
  const [time, setTime] = useState("");
  const [startTime, setstartTime] = useState("");
  const [endTime, setEndTime] = useState("");

  const [allUsers, setallUsers] = useState([
    {
      inserteDateTime: "",
      userName: "",
    },
  ]);
  var x = new Date().getTimezoneOffset();
  var location = Intl.DateTimeFormat().resolvedOptions().timeZone;
  console.log("Location" + location);

  function getOffSet() {
    axios.get(`http://localhost:8080/date/${x}/jitu`).then((res) => {
      console.log("called api");
      const time = res.data;
      setTime(time);
    });
    //setTime(x);
  }

  function sendTimeBound(e) {
    axios.get(`http://localhost:8080/all/${x}`).then((res) => {
      console.log("Show TimeZone based on Location");
      setallUsers(res.data);
    });
  }

  return (
    <div className="App">
      <header className="App-header">
        <h4>Current time : {time}</h4>
        <vaddin-button variant="filled" onClick={getOffSet}>
          Get Time
        </vaddin-button>

        <form onSubmit={sendTimeBound}>
          <vaadin-time-picker
            label="From"
            onChange={(e) => {
              setstartTime(e.target.value);
            }}
          ></vaadin-time-picker>
          <vaadin-time-picker
            label="To"
            onChange={(e) => {
              setEndTime(e.target.value);
            }}
          ></vaadin-time-picker>
          <vaddin-button
            type="submit"
            onClick={sendTimeBound}
            style={{
              color: "white",
              padding: "5px",
              margin: "5px",
              fontSize: "15pt",
              backgroundColor: "rgb(43, 47, 87)",
              borderRadius: "5px",
            }}
          >
            Get report
          </vaddin-button>
        </form>
        {allUsers.map((user) => {
          return (
            <ul
              style={{
                padding: "10px",
                backgroundColor: "#d5f1f5",
                borderRadius: "5px",
              }}
            >
              <h4>{user.userName}</h4>
              <h5>{location}</h5>
              {user.inserteDateTime}
            </ul>
          );
        })}
      </header>
    </div>
  );
}

export default App;
