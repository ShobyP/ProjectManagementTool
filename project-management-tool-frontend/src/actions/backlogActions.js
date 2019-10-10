import axios from "axios";
import { GET_ERRORS } from "./types";
//import {  } from "./types";

export const addProjectTask = (
  backlogID,
  projectTask,
  history
) => async dispatch => {
  try {
    await axios.post(`/api/backlog/${backlogID}`, projectTask);
    history.push(`/projectBoard/${backlogID}`);
    dispatch({ type: GET_ERRORS, payload: {} });
  } catch (error) {
    dispatch({ type: GET_ERRORS, payload: error.response.data });
  }
};
