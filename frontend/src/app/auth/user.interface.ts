export type UserRole = 'ADMIN' | 'USER';

export interface User {
  id: number;
  name: string;
  email: string;
  role: UserRole;
}

export interface LoginResponse {
  token: string;
  user?: User;
  role: UserRole;
}
