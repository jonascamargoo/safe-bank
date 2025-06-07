import { TransactionType } from "../domains/TransactionType";


export interface TransactionDTO {
  id: number;
  value: number;
  transactionTimestamp: string;
  transactionType: TransactionType;
  accountId: number;
}