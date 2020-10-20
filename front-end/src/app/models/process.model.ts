import { Client } from './client.model'

export class Process {
    id: number
    name: string
    feedback: string
    responsibleClients: Client[] = []
    finalized: boolean
}