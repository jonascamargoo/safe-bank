import { TransactionType } from "../domains/TransactionType";

export interface TransactionDTO {
  id: number;
  value: number;
  type: TransactionType;
  transactionDate: string;
  accountNumber: string;
  originTransactionId: number | null;

}