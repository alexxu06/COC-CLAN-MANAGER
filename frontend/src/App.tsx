import axios from "axios"
import { useState } from "react";

function App() {
  const [thing, setThing] = useState("asdas");
  
  const test = () => {
    axios.get("/api/clan", {
      params: {
        tag: "2GLY9GRG9"
      }
    })
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
      <h1>a</h1>
      <p>hi</p>
      <button onClick={test}>click</button>
    </div>
  )
}

export default App
