import { combineReducers, configureStore, getDefaultMiddleware } from "@reduxjs/toolkit";
import { persistReducer, persistStore } from "redux-persist";
import storage from "redux-persist/es/storage";
import sessionStorage from 'redux-persist/lib/storage/session';

import authReducer from './authSlice';
import { PersistPartial } from "redux-persist/es/persistReducer";

export type RootState = {
    auth: ReturnType<typeof authReducer> & PersistPartial;
};

const authPersistConfig = {
    key: 'auth',
    storage: sessionStorage,
};

const rootReducer = combineReducers<RootState>({
    auth: persistReducer(authPersistConfig, authReducer),
});

export const store = configureStore({
    reducer: rootReducer,
    middleware: getDefaultMiddleware({
        serializableCheck: false,
    }),
});

export const persistor = persistStore(store);