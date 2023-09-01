import { createAction, createSlice, PayloadAction } from '@reduxjs/toolkit';

interface User {
  id: string;
  username: string;
  phoneNumber: string;
  role: string;
}

interface AuthState {
  user: User | null;
  token: string | null;
  role: string | null;
  verified: boolean;
  blocked: boolean;
  isLogged: boolean;
}

const initialState: AuthState = {
  user: null,
  token: null,
  role: null,
  verified: false,
  blocked: false,
  isLogged: false
};

export const authSlice = createSlice({
  name: 'auth',
  initialState,
  reducers: {
    login: (state, action: PayloadAction<{user: User | null, token: string | null, role: string, verified: boolean, blocked: boolean }>) => {
      if (action.payload.token !== null) {
        state.token = action.payload.token;
      }
      state.user = action.payload.user;
      state.role = action.payload.role;
      state.blocked = action.payload.blocked;
      state.verified = action.payload.verified
      state.isLogged = true;
    },
    changeStatus: (state, action: PayloadAction<{verified: boolean, blocked: boolean }>) => {
      console.log(action);
      state.blocked = action.payload.blocked;
      state.verified = action.payload.verified;
    },
    logout: (state) => {
      state.user = null;
      state.token = null;
      state.role = null;
      state.blocked = false;
      state.verified = false;
      state.isLogged = false;
    }
  },
});

export const { login, logout, changeStatus } = authSlice.actions;
export const setToken = createAction<string>('auth/setToken');

export default authSlice.reducer;