import { createStore, applyMiddleware, compose } from "redux";
import thunk from "redux-thunk";
import rootReducer from "./reducers";

const initialState = {};

// instructor has it as [thunk]
const middleware = [thunk];

let store;

// create store if browser is chrome.
/*

createStore from redux creates the store.
1  - rootReducer from reducers
2  - initial state as initial setup.
3  - compose to apply middleware. both from redux library

this can be a convention/sequence.
*/

if (window.navigator.userAgent.includes("Chrome")) {
  store = createStore(
    rootReducer,
    initialState,
    compose(
      applyMiddleware(...middleware),
      window.__REDUX_DEVTOOLS_EXTENSION__ &&
        window.__REDUX_DEVTOOLS_EXTENSION__()
    )
  );
} else {
  store = createStore(
    rootReducer,
    initialState,
    compose(applyMiddleware(...middleware))
  );
}

export default store;
