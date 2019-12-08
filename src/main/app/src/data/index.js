import { combineReducers } from "redux";
import type { Dispatch } from 'redux';

import { routerReducer } from "react-router-redux";

import auth from './modules/auth';
import books from './modules/books';
import news from './modules/news';
import preference from "./modules/preference";
import websockets from './modules/websockets';
import register from './modules/register';

const rootReducer = combineReducers({
    router: routerReducer,
    auth,
    books,
    news,
    preference,
    websockets,
    register,
});

export default rootReducer;

export type Thunk<A> = (dispatch: Dispatch<A>, getState: () => Object) => any;