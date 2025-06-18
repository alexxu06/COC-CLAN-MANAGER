import axios from "axios"
import { useState } from "react";

function App() {
  const [thing, setThing] = useState("asdas");
  
  const test = () => {
    axios.get("/api/test")
    .then(function (response) {
        console.log(response)
        setThing(response.data)
    })
    .catch(function (error) {
        console.log(error)
        alert(error.response.data);
    });
  }

  return (
    <div>
      <h1>{thing}</h1>
      <p>hi</p>
      <button onClick={test}>click</button>
    </div>
  )
}

export default App
