import axios from "axios";
//import {  } from "./types";

export const addProjectTask = (
  backlogID,
  projectTask,
  history
) => async dispatch => {
  //happy path
  await axios.post(`/api/backlog/${backlogID}`, projectTask);
  history.push(`projectBoard/${backlogID}`);
};
